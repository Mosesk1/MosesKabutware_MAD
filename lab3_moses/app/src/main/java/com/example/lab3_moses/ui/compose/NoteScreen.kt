@file:OptIn(ExperimentalFoundationApi::class)

package com.example.lab3_moses.ui.compose

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lab3_moses.model.Note
import com.example.lab3_moses.model.NoteViewModel
import java.util.*
import com.example.lab3_moses.R

@Composable
fun NoteScreen() {
    val viewModel: NoteViewModel = viewModel()

    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        backgroundColor = MaterialTheme.colors.surface,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {showDialog = true},
            )
            {
                Icon(Icons.Filled.Add, contentDescription = "")
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        content = {
            if (showDialog){
                addItemDialog(context, dismissDialog = {showDialog = false}, {viewModel.addNote(it)})
            }
            LazyColumn(
                contentPadding = PaddingValues(
                    vertical = 8.dp,
                    horizontal = 8.dp
                )
            )
            {
                items(viewModel.noteList, key={item -> item.id}) { item ->
                    BookItem(item = item, context, {viewModel.deleteNote(it)})
                }
            }
        }
    )
}

@Composable
fun addItemDialog(context: Context, dismissDialog:() -> Unit, addItem: (Note) -> Unit){
    var itemTitleField by remember {
        mutableStateOf("")
    }
    var itemDescriptionField by remember {
        mutableStateOf("")
    }

    AlertDialog(
        onDismissRequest = { dismissDialog},
        title={Text(text = stringResource(id = R.string.addItem), style = MaterialTheme.typography.h6)},
        text = {
            Column(modifier = Modifier.padding(top=20.dp)) {
                TextField(label = {Text(text= stringResource(id = R.string.itemTitle))}, value = itemTitleField, onValueChange = {itemTitleField=it})
                Spacer(modifier = Modifier.height(10.dp))
                TextField(label = {Text(text= stringResource(id = R.string.description))},value = itemDescriptionField, onValueChange = {itemDescriptionField=it})
            }
        },
        confirmButton = {
            Button(onClick = {
                if(itemTitleField.isNotEmpty()) {
                    val newID = UUID.randomUUID().toString();
                    addItem(Note(newID, itemTitleField, itemDescriptionField))
                    Toast.makeText(
                        context,
                        context.resources.getString(R.string.itemTitle),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                dismissDialog()
            },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Black)
            )
            {
                Text(text = stringResource(id = R.string.add), color = Color.White)
            }
        },dismissButton = {
            Button(onClick = {
                dismissDialog()
            },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Black)
            ) {
                Text(text = stringResource(id = R.string.cancel), color = Color.White)
            }
        }
    )
}


@Composable
fun deleteItemDialog(context: Context, dismissDialog:() -> Unit, item: Note, deleteNote: (Note) -> Unit){
    AlertDialog(
        onDismissRequest = { dismissDialog},
        title={Text(text = stringResource(id = R.string.delete), style = MaterialTheme.typography.h6)},
        confirmButton = {
            Button(onClick = {
                deleteNote(item)
                Toast.makeText(
                    context,
                    context.resources.getString(R.string.deleteItem),
                    Toast.LENGTH_SHORT
                ).show()
                dismissDialog()
            },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Black)
            )
            {
                Text(text = stringResource(id = R.string.yes), color = Color.White)
            }
        },dismissButton = {
            Button(onClick = {
                dismissDialog()
            },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Black)
            ) {
                Text(text = stringResource(id = R.string.no), color = Color.White)
            }
        }
    )
}

@Composable
fun BookItem(item: Note, context: Context, deleteNote: (Note) -> Unit) {
    var showDeleteDialog by remember { mutableStateOf(false) }
    val checkedState = remember { mutableStateOf(false) }
    Row {
        Card(
            elevation = 4.dp,
            shape = RoundedCornerShape(10.dp),
            backgroundColor = MaterialTheme.colors.primary,
            contentColor = MaterialTheme.colors.onSecondary,
            border = BorderStroke(2.dp, color = MaterialTheme.colors.primaryVariant),
            modifier = Modifier
                .padding(8.dp)
                .width(330.dp)
                .combinedClickable(
                    onClick = {
                        Toast
                            .makeText(
                                context,
                                context.resources.getString(R.string.readmsg) + " " + item.noteTitle +" first",
                                Toast.LENGTH_SHORT
                            )
                            .show()
                    },
                    onLongClick = { showDeleteDialog = true }
                )
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(
                    modifier = Modifier.padding(14.dp)
                ) {
                    Text(text = item.noteTitle, style = MaterialTheme.typography.h6)
                    Text(text = item.noteDescription, style = MaterialTheme.typography.body1)
                }
            }

        }
        Checkbox(
            checked = checkedState.value,
            modifier = Modifier
                .align(Alignment.CenterVertically),
            onCheckedChange = { checkedState.value = it }
        )
    }
    if (showDeleteDialog){
        deleteItemDialog(context, dismissDialog = {showDeleteDialog = false}, item, deleteNote)
    }
}
