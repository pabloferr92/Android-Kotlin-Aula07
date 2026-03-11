package com.example.projetoaula07

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.projetoaula07.ui.theme.ProjetoAula07Theme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
    val nome: String,
    val nota: Double,
    var selecionado : Boolean = false
)

    val alunosList = mutableListOf<Aluno>(
        Aluno("Pablo", 10.0),
        Aluno("Pablo", 10.0),
        Aluno("Pablo", 10.0),
        Aluno("Pablo", 10.0),
        Aluno("Pablo", 10.0),
        Aluno("Pablo", 10.0),
        Aluno("Pablo", 10.0),
        Aluno("Pablo", 10.0),
        Aluno("Pablo", 10.0),
        Aluno("Pablo", 10.0),
        Aluno("Pablo", 10.0),
    )

@Composable
fun ListAlunosScreen() {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        itemsIndexed(alunosList) { index, item ->
            AlunoItem(index,item)
        }
    }
}



@Composable
fun AlunoItem(index : Int,aluno: Aluno) {

    var cor by remember { mutableStateOf(Color.Black) }


    var selecionado by remember { mutableStateOf(-1) } // estado global


    val scope = rememberCoroutineScope()


    Surface(
        modifier = Modifier.fillMaxWidth()
            .clickable {

                scope.launch {

                    cor = Color.Red
                    delay(3000)
                    cor = Color.Black

                }
            }
        ,

        shape = RoundedCornerShape(12.dp),
        tonalElevation = 3.dp
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp),
        ) {
            Text(
                text = aluno.nome,
                style = MaterialTheme.typography.titleLarge,
                color = cor,
            )
            Text(
                text = "R$ ${aluno.nota}",
                style = MaterialTheme.typography.bodyMedium
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