package com.tanya.planner.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.tanya.planner.R
import com.tanya.planner.database.entity.WorkItem
import com.tanya.planner.design.black100
import com.tanya.planner.design.black60
import com.tanya.planner.design.blue60
import com.tanya.planner.design.grey10
import com.tanya.planner.design.grey3
import com.tanya.planner.design.grey5
import com.tanya.planner.design.grey50
import com.tanya.planner.design.red60
import com.tanya.planner.design.white100
import com.tanya.planner.model.RecentTaskItemModel
import com.tanya.planner.model.TaskPriority
import com.tanya.planner.model.TaskStatus
import com.tanya.planner.ui.theme.PlannerAppBar
import com.tanya.planner.viewmodel.home.AddTaskViewModel
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddWorkItemScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    addTaskViewModel: AddTaskViewModel = hiltViewModel(),
    recentTaskItemModel: RecentTaskItemModel?,
) {
    var title by remember { mutableStateOf(recentTaskItemModel?.title.orEmpty()) }
    var description by remember { mutableStateOf(recentTaskItemModel?.description.orEmpty()) }

    var selectedDueDate by remember { mutableStateOf<LocalDate?>(recentTaskItemModel?.dueDate) }
    var selectedPriority by remember {
        mutableStateOf(
            recentTaskItemModel?.priority ?: TaskPriority.LOW
        )
    }
    var selectedStatus by remember {
        mutableStateOf(
            recentTaskItemModel?.status ?: TaskStatus.TODO
        )
    }

    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()

    var showDeleteConfirmation by remember { mutableStateOf(false) }

    Scaffold(
        modifier = modifier,
        containerColor = white100,
        topBar = {
            PlannerAppBar(title = if (title.isEmpty()) "New Task" else "Edit Task") { }
        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(white100)
                    .padding(horizontal = 16.dp, vertical = 12.dp),
            ) {
                if (recentTaskItemModel != null) {
                    IconButton(
                        onClick = { showDeleteConfirmation = true },
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = null,
                            tint = red60,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
                Button(
                    enabled = title.isNotEmpty(),
                    onClick = {
                        addTaskViewModel.saveTask(
                            WorkItem(
                                id = recentTaskItemModel?.id ?: 0,
                                title = title,
                                description = description,
                                dueDate = selectedDueDate,
                                status = selectedStatus.name,
                                priority = selectedPriority.name
                            )
                        )
                        navController.popBackStack()
                    },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = blue60,
                        contentColor = white100,
                    ),
                    shape = RoundedCornerShape(12.dp),
                ) {
                    Row {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.size(8.dp))
                        Text(
                            text = "Save changes",
                            style = TextStyle(
                                fontSize = 14.sp,
                                fontWeight = FontWeight.W600,
                            )
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        if (showDatePicker) {
            DatePickerDialog(
                onDismissRequest = { showDatePicker = false },
                confirmButton = {
                    TextButton(
                        onClick = {
                            val selectedMillis = datePickerState.selectedDateMillis
                            if (selectedMillis != null) {
                                selectedDueDate = Instant
                                    .ofEpochMilli(selectedMillis)
                                    .atZone(ZoneId.systemDefault())
                                    .toLocalDate()
                            }
                            showDatePicker = false
                        }
                    ) {
                        Text("OK")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDatePicker = false }) {
                        Text("Cancel")
                    }
                }
            ) {
                DatePicker(state = datePickerState)
            }
        }
        Column(
            modifier = Modifier
                .padding(innerPadding)
        ) {
            TextField(
                value = title,
                onValueChange = { title = it },
                modifier = Modifier
                    .fillMaxWidth(),
                placeholder = {
                    Text(
                        text = "Task Title",
                        style = TextStyle(
                            fontSize = 30.sp,
                            fontWeight = FontWeight.W700,
                            color = grey50,
                        )
                    )
                },
                textStyle = TextStyle(
                    fontSize = 30.sp,
                    fontWeight = FontWeight.W700,
                    color = black100,
                ),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = white100,
                    unfocusedContainerColor = white100,
                    focusedIndicatorColor = white100,
                    unfocusedIndicatorColor = white100,
                ),
            )
            HorizontalDivider(color = grey5, modifier = Modifier.padding(horizontal = 16.dp))
            TextField(
                value = description,
                onValueChange = { description = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(
                        text = "Add notes or description here...",
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.W400,
                            color = grey50,
                        )
                    )
                },
                textStyle = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.W400,
                    color = black100,
                ),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = white100,
                    unfocusedContainerColor = white100,
                    focusedIndicatorColor = white100,
                    unfocusedIndicatorColor = white100,
                ),
                minLines = 5,
            )

            HorizontalDivider(color = grey3, thickness = 12.dp)
            Column(
                modifier = Modifier
                    .weight(1f)
                    .background(white100)
                    .padding(horizontal = 20.dp)
            ) {
                SettingRow(
                    icon = painterResource(R.drawable.ic_calendar_big),
                    iconTint = blue60,
                    iconBackground = blue60.copy(alpha = 0.1f),
                    label = "Due Date",
                    value = selectedDueDate?.format(DateTimeFormatter.ofPattern("dd MMM yyyy"))
                        ?: "Set Due Date",
                    onClick = {
                        showDatePicker = true
                    },
                )

                Spacer(modifier = Modifier.height(8.dp))
                SettingRow(
                    icon = painterResource(R.drawable.ic_exclamation),
                    iconTint = selectedPriority.getIconColor(),
                    iconBackground = selectedPriority.getBackgroundColor(),
                    label = "Priority",
                    value = selectedPriority.name,
                    options = TaskPriority.entries,
                    onOptionSelected = { selectedPriority = it },
                )

                Spacer(modifier = Modifier.height(8.dp))
                SettingRow(
                    icon = painterResource(selectedStatus.icon()),
                    iconTint = selectedStatus.iconColor(),
                    iconBackground = selectedStatus.backgroundColor(),
                    label = "Status",
                    value = selectedStatus.uiLabel,
                    options = TaskStatus.entries,
                    onOptionSelected = { selectedStatus = it }
                )
            }
        }

        if (showDeleteConfirmation && recentTaskItemModel != null) {
            DeleteTaskBottomSheet(
                onDismiss = { showDeleteConfirmation = false },
                onConfirmDelete = {
                    addTaskViewModel.deleteTask(recentTaskItemModel)
                    navController.popBackStack()
                },
            )
        }
    }
}

@Composable
private fun <T : Enum<T>> SettingRow(
    icon: Painter,
    label: String,
    value: String,
    onClick: (() -> Unit)? = null,
    options: List<T> = emptyList(),
    onOptionSelected: (T) -> Unit = {},
    iconBackground: Color,
    iconTint: Color,
) {
    var statusExpanded by remember { mutableStateOf(false) }
    Box {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = onClick ?: {
                        statusExpanded = true
                    })
                    .padding(vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(color = iconBackground),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        painter = icon,
                        contentDescription = null,
                        tint = iconTint,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Spacer(modifier = Modifier.size(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = label,
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontWeight = FontWeight.W500,
                            color = black60,
                        )
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = value,
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.W600,
                            color = black100,
                        )
                    )
                }
                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = null,
                    tint = grey50,
                )
            }
            HorizontalDivider(
                color = grey10,
                modifier = Modifier.padding(start = 30.dp)
            )
        }
        DropdownMenu(
            expanded = statusExpanded,
            onDismissRequest = { statusExpanded = false },
            containerColor = white100,
        ) {
            options.forEach { status ->
                DropdownMenuItem(
                    text = { Text(text = (status as? TaskStatus)?.uiLabel ?: status.name) },
                    onClick = {
                        onOptionSelected(status)
                        statusExpanded = false
                    },
                )
            }
        }
    }
}