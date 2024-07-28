/**
 * A Screen in the app itself, which can be arrived at when the user clicks an existing
 * progression item tracker they made in ProgressScreen.kt
 *
 * It will display a list of all the data points in the current progression tracker, and a chart
 * to visualize the data.
 *
 * Collects state and makes database queries to load the current ProgressItem data points (List of displayed ProgressDataPoints)
 * from a viewModel object in ProgressDataViewModel.kt
 *
 * Current state of the data points automatically updates the chart composable
 * */


package com.example.flexapp.screens

import android.graphics.Color
import android.graphics.Typeface
import android.text.TextUtils
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.flexapp.database.AppViewModelProvider
import com.example.flexapp.database.entities.ProgressDataPoint
import com.example.flexapp.navigation.GoBackTopAppBar
import com.example.flexapp.screens.viewmodels.ProgressDataDetails
import com.example.flexapp.screens.viewmodels.ProgressDataEntryUiState
import com.example.flexapp.screens.viewmodels.ProgressDataViewModel
import com.example.flexapp.ui.theme.FlexAppTheme
import com.example.flexapp.ui.theme.primaryLight
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.rememberStartAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.compose.component.shape.shader.fromBrush
import com.patrykandpatrick.vico.core.axis.AxisItemPlacer
import com.patrykandpatrick.vico.core.axis.AxisPosition
import com.patrykandpatrick.vico.core.axis.formatter.AxisValueFormatter
import com.patrykandpatrick.vico.core.chart.layout.HorizontalLayout
import com.patrykandpatrick.vico.core.chart.line.LineChart
import com.patrykandpatrick.vico.core.component.shape.shader.DynamicShaders
import com.patrykandpatrick.vico.core.component.text.textComponent
import com.patrykandpatrick.vico.core.entry.entryModelOf
import com.patrykandpatrick.vico.core.entry.entryOf
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProgressItemScreen(
    navigateBack: () -> Unit,
    progressItemId: Long,
    progressItemName: String,
    progressValueType: String,
    viewModel: ProgressDataViewModel = viewModel(factory = AppViewModelProvider.Factory)
){
    var editMode: Boolean by remember { mutableStateOf(false) }
    var addDataDialog by remember { mutableStateOf(false) }

    val progressDataUiState by viewModel.progressDataUiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()


    Scaffold(
        topBar = {
            GoBackTopAppBar(
                title = progressItemName,
                canNavigateBack = true,
                navigateUp = navigateBack,
                allowEdit = true,
                editAction = {editMode = !editMode}
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { addDataDialog = true },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(20.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Data"
                )
            }
        }

    ) {innerPadding ->
        ProgressItemBody(
            modifier = Modifier.padding(innerPadding),
            dataPoints = progressDataUiState.progressDataPoints,
            onDeleteDataPoint = { progressDataPoint: ProgressDataPoint ->
                coroutineScope.launch{
                    viewModel.deleteDataPoint(progressDataPoint)
                }
            },
            metric = progressValueType,
            editMode = editMode,
        )

        if (addDataDialog) {
            editMode = false
            AddDataDialog(
                progressDataEntryUiState = viewModel.progressDataEntryUiState,
                metric = progressValueType,
                onSaveDataPoint = {
                    coroutineScope.launch {
                        viewModel.saveProgressDataPoint()
                        addDataDialog = false
                    }
                },
                onDismiss = { addDataDialog = false },
                onValueChange = viewModel::updateDataEntryState
            )
        }

    }
}

@Composable
fun ProgressItemBody(
    modifier: Modifier,
    dataPoints: List<ProgressDataPoint>,
    onDeleteDataPoint: (ProgressDataPoint) -> Unit,
    metric: String,
    editMode: Boolean,
){
    Column(
        modifier = modifier
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        if (dataPoints.isEmpty()) {
            Text(
                text = "No data added yet",
                textAlign = TextAlign.Center,
            )
        } else {

            Column(
                modifier = Modifier.padding(vertical = 16.dp)
            ){
                val data = dataPoints.map {
                    LocalDate.parse(it.dateString) to it.value.toFloat()
                }.sortedBy { it.first.toEpochDay() }

                val xValuesToDates = data.associate { it.first.toEpochDay().toFloat() to it.first }

                val chartEntryModel = entryModelOf(data.map { entryOf(it.first.toEpochDay().toFloat(), it.second) })

                val dateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("d MMM")

                val horizontalAxisValueFormatter = AxisValueFormatter<AxisPosition.Horizontal.Bottom> { value, _ ->
                    (xValuesToDates[value] ?: LocalDate.ofEpochDay(value.toLong())).format(dateTimeFormatter)
                }

                val lineSpecData = arrayListOf<LineChart.LineSpec>()
                lineSpecData.add(
                    LineChart.LineSpec(
                        lineColor = primaryLight.toArgb(),
                        lineBackgroundShader = DynamicShaders.fromBrush(
                            brush = Brush.verticalGradient(
                                listOf(
                                    primaryLight.copy(com.patrykandpatrick.vico.core.DefaultAlpha.LINE_BACKGROUND_SHADER_START),
                                    primaryLight.copy(com.patrykandpatrick.vico.core.DefaultAlpha.LINE_BACKGROUND_SHADER_END)
                                )
                            )
                        )
                    )
                )
                var xValuePlacer = AxisItemPlacer.Horizontal.default(spacing = 1, addExtremeLabelPadding = true)
                if(dataPoints.size > 2){
                    xValuePlacer = AxisItemPlacer.Horizontal.default(spacing = 10, addExtremeLabelPadding = true)
                }

                Chart(
                    chart = lineChart(
                        lines = lineSpecData,
                        spacing = 8.dp
                    ),
                    model = chartEntryModel,
                    startAxis = rememberStartAxis(
                        titleComponent = textComponent {
                            color = Color.BLACK
                            textSizeSp = 16f
                            typeface = Typeface.MONOSPACE
                            ellipsize = TextUtils.TruncateAt.END
                        },
                        title = metric
                    ),
                    bottomAxis = rememberBottomAxis(
                        valueFormatter = horizontalAxisValueFormatter,
                        itemPlacer = xValuePlacer,
                        tickLength = 0.dp
                    ),
                    horizontalLayout = HorizontalLayout.FullWidth(),

                )

            }
            ProgressDataPointList(
                dataPoints = dataPoints.sortedBy { LocalDate.parse(it.dateString).toEpochDay() },
                editMode = editMode,
                metric = metric,
                onDeleteDataPoint = onDeleteDataPoint,
                modifier = Modifier.padding(horizontal = 8.dp),
                contentPadding = PaddingValues(vertical = 8.dp)
            )
        }
    }
}

@Composable
private fun ProgressDataPointList(
    dataPoints: List<ProgressDataPoint>,
    editMode: Boolean,
    metric: String,
    onDeleteDataPoint: (ProgressDataPoint) -> Unit,
    modifier: Modifier,
    contentPadding: PaddingValues
){
    LazyColumn(
        modifier = Modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = contentPadding
    ){
        items(items = dataPoints, key = {it.dataId}){ progressDataPoint ->
            ProgressDataItem(
                progressDataPoint = progressDataPoint,
                editMode = editMode,
                metric = metric,
                onDeleteDataPoint = onDeleteDataPoint
            )

        }
    }

}
@Composable
private fun ProgressDataItem(
    progressDataPoint: ProgressDataPoint,
    editMode: Boolean,
    metric: String,
    onDeleteDataPoint: (ProgressDataPoint) -> Unit
){
        Box(
            modifier = Modifier.fillMaxWidth()
        ){
            Row(
                modifier = Modifier.padding(8.dp)
            ){
                Text(
                    modifier = Modifier.weight(4f),
                    text = "${progressDataPoint.value} $metric  -  ${progressDataPoint.dateString}"
                )
                if(editMode){
                        Box(
                            contentAlignment = Alignment.TopEnd,
                            modifier = Modifier.clickable { onDeleteDataPoint(progressDataPoint) }
                        ){
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete"
                            )
                        }

                }
            }
        }

}
@Composable
private fun AddDataDialog(
    progressDataEntryUiState: ProgressDataEntryUiState,
    metric: String,
    onSaveDataPoint: () -> Unit,
    onDismiss: () -> Unit,
    onValueChange: (ProgressDataDetails) -> Unit = {}
)
{

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Add Data Point") },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                TextField(
                    value = progressDataEntryUiState.progressDataDetails.value,
                    onValueChange = {onValueChange(progressDataEntryUiState.progressDataDetails.copy(value = it))},
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    label = {Text("Value in $metric")}
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = progressDataEntryUiState.progressDataDetails.dateString,
                    onValueChange = { onValueChange(progressDataEntryUiState.progressDataDetails.copy(dateString = it)) },
                    label = { Text("Date: yyyy-MM-dd") }
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onSaveDataPoint()
                },
                enabled = progressDataEntryUiState.isEntryValid
            ) {
                Text("Save")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun ProgressDataPointListPreview(){

    FlexAppTheme {
        ProgressDataPointList(
            dataPoints = listOf(
                ProgressDataPoint(1,135,"2022-10-05",0),
                ProgressDataPoint(2,150,"2022-11-02",0),
                ProgressDataPoint(3,170,"2022-12-14",0)
            ),
            editMode = true,
            modifier = Modifier,
            contentPadding = PaddingValues(8.dp),
            metric = "lbs",
            onDeleteDataPoint = {}

        )

    }
}