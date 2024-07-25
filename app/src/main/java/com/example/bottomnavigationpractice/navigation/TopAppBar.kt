/** A kotline file that contains a
 * commonly used component in many screens,
 *
 * a top app bar 'GoBackTopAppBar' with an icon to navigate back to the previous screen,
 * with an optional argument, 'allowEdit', a boolean which enables
 * the icon to edit some kind of data. If enabled, an argument should
 * be passed to editAction() -> Unit = {} to override the empty action
 *
 * */


package com.example.bottomnavigationpractice.navigation
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GoBackTopAppBar(
    title: String,
    canNavigateBack: Boolean,
    allowEdit: Boolean = false,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    navigateUp: () -> Unit = {},
    editAction: () -> Unit = {},
) {
    CenterAlignedTopAppBar(
        title = { Text(title) },
        modifier = modifier,
        scrollBehavior = scrollBehavior,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Go back"
                    )
                }
            }
        },
        actions = {
            if(allowEdit) {
                IconButton(onClick = { editAction() }) {
                    Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit")
                }
            }
        }
    )
}