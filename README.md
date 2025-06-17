# TestCompose

#2.1 Первое приложение на JC

Создадим приложение. Уберём лишнее. Осталось

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { //последний параметр как лямбда-выражение
            Greeting("Android")
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(
        text = "Hello $name!"
    )
}

@Composable - функция, которая участвует в отрисовке UI
Text - стандартная Composable - функция
@Composable - функция - это обычная функция. Но есть два отличия 
1) Нужно отмечть c аннтоацией @Composable
2) Писать название с большой буквы, т.к. она описывает не действие, а состояние

Замечание
Composable функции нельзя вызывать в любом месте - только внутри Composable-функций