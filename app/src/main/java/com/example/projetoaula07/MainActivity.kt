package com.example.projetoaula07

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.projetoaula07.ui.theme.ProjetoAula07Theme
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProjetoAula07Theme {
                ListAlunosScreen()
            }
        }
    }
}


data class Aluno(
    val id: Int,
    val nome: String,
    val nota: Double,
    var selecionado: Boolean = false
)

fun getNextID(alunoList: SnapshotStateList<Aluno>): Int {

    var lastID = 0
    alunoList.forEach { item ->
        if (item.id > lastID) lastID = item.id
    }

    return lastID + 1

}


@Composable
fun ListAlunosScreen() {

    val alunosList = remember {
        mutableStateListOf<Aluno>(
            Aluno(1, "Pablo", 10.0),
            Aluno(2, "Pablo", 10.0),
            Aluno(3, "Pablo", 10.0),
            Aluno(5, "Pablo", 10.0),
            Aluno(6, "Pablo", 10.0),
            Aluno(7, "Pablo", 10.0),
            Aluno(8, "Pablo", 10.0),
        )
    }

    var alunoSelecionadoID by remember { mutableStateOf(-1) } // estado global

    val lastID = getNextID(alunosList)

    var texto by remember { mutableStateOf("") }
    var proximoIdState by remember { mutableStateOf(lastID) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(25.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = texto,
            onValueChange = { texto = it },
            label = { Text("Novo Aluno") },
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = {
                if (texto.isNotBlank()) {
                    alunosList.add(Aluno(proximoIdState, texto, 10.0))
                    proximoIdState = getNextID(alunosList)
                    texto = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Adicionar")
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            items(alunosList, key = { it.id }) { aluno ->
                AlunoItem(
                    aluno,
                    selecionado = aluno.id == alunoSelecionadoID,
                    onClick = { alunoSelecionadoID = aluno.id },
                    onRemove = { alunosList.remove(aluno) }
                )
            }
        }
    }

}

@Composable
fun AlunoItem(aluno: Aluno, selecionado: Boolean, onClick: () -> Unit, onRemove: () -> Unit) {

    var offSetX by remember { mutableStateOf(0f) }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .offset { IntOffset(0, offSetX.roundToInt()) }
            .clickable(onClick = onClick)
            .draggable(
                orientation = Orientation.Vertical,
                enabled = true,
                state = rememberDraggableState { delta ->
                    offSetX += delta
                    offSetX = offSetX + delta
                }),
        shape = RoundedCornerShape(12.dp),
        tonalElevation = 3.dp
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = aluno.nome,
                style = MaterialTheme.typography.titleLarge,
                color = if (selecionado) Color.Red else Color.Black,
            )
            Text(
                text = "Nota ${aluno.nota}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "x",
                modifier = Modifier
                    .clickable(onClick = onRemove)
                    .border(4.dp, Color.Black, CutCornerShape(1.dp))
                    .padding(8.dp) // espaço interno
                ,
                color = Color.Red
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ProjetoAula07Theme {
        ListAlunosScreen()
    }
}