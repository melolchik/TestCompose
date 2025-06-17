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


#2.2 Preview и Column

Чтобы видеть представление view нужно использовать аннтоацию @Preview но функция должна быть без параметров

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UserInfo(name = "Olga", age = 42)
        }
    }
}


@Composable
fun UserInfo(name: String, age : Int){
    Text(text = "Hello $name. You are $age age")
}

@Preview <---------------------
@Composable
fun Greeting() {
    val name = "Jone"
    Text(
        text = "Hello $name!"
    )
}

Для функции UserInfo

@Preview
@Composable
fun UserInfoPreview(){
    UserInfo(name = "John", age = 33)
}

Далее , вызовем в UserInfo функцию Text дважды

fun UserInfo(name: String, age : Int){
    Text(text = "Hello $name. You are $age age")
    Text(text = "Hello $name. You are $age age")
}

В Preview ничего не поменяется - текст наложится друг на друга

Чтобы строчки располагались друг под другом, нужно использовать Column()

@Composable
fun UserInfo(name: String, age : Int){
    Column {
        Text(text = "Hello $name. You are $age age")
        Text(text = "Hello $name. You are $age age")
    }
}

выведем 10 раз Text

@Composable
fun UserInfo(name: String, age : Int){
    Column {
        repeat(10) {
            Text(text = "Hello $name. You are $age age")
        }

    }
}