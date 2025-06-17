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

#2.3 Row, Box, Modifier

Сделаем что-то напопдбие таблицы умножения

1) Выведем столбец чисел от 1 до 9
@Preview
@Composable
fun TimesTable(){
    Column{
        for(i in 1 ..9){
            Text(text = "$i")
        }
    }
}

Column - аналог LinerLayout с вертикальной ориентаций

2) В каждом столбце выведем строки с цифрами. Аналог LinerLayout с горизонтальной ориентацией Row


@Preview
@Composable
fun TimesTable(){
//    Column{
//        for(i in 1 ..9){
//            Text(text = "$i")
//        }
//    }

    Row{  <---- все числа будут выведены в одну строку
        for(i in 1 ..9){
            Text(text = "$i")
        }
    }
}


@Preview
@Composable
fun TimesTable(){
    Column{
        for(i in 1 ..9){
            Row{
                for(j in 1 ..9){
                   Text(text = "${i*j}")
                }
            }
        }
    }
}

Таблица умножения готова, но читать её не удобно,т.к. она не отформатирована

Modifier - используется для модификации каким-то образом элементов, например добавлять отступы, устанавливать высоту/ширину

fun TimesTable(){
    Column (modifier = Modifier.background(color = Color.Yellow) <- цвет фона
                                .padding(all = 16.dp) <---- отступ колонки
                                .fillMaxSize()) <---------- всё пространоство по высоте и ширине
        {
        for(i in 1 ..9){
            Row{
                for(j in 1 ..9){
                    Text(text = "${i*j}")
                }
            }
        }
    }
}

Далее выравнивание по вертикали

@Preview
@Composable
fun TimesTable(){
    Column (modifier = Modifier.background(color = Color.Yellow)
                                .padding(all = 16.dp)
                                .fillMaxSize())
        {
        for(i in 1 ..9){
            Row(modifier = Modifier
                .fillMaxWidth()
                .weight(1f) - аналог weight в LinerLayout

            ){
                for(j in 1 ..9){
                    Text(text = "${i*j}")
                }
            }
        }
    }
}
Добавим для каждого элемента доп.конейнер Box

	Row(modifier = Modifier
                .fillMaxWidth()
                .weight(1f)

            ){
                for(j in 1 ..9){
                    Box(modifier = Modifier.fillMaxHeight() <--- Box с модификаторами
                        .weight(1f)
						.border(width = 1.dp, color = Color.Gray), <---- border for Box
						contentAlignment = Alignment.Center) <--- выравнивание содержимого по центру
					{
                        Text(text = "${i * j}")
                    }
                }
            }
			
Итог

@Preview
@Composable
fun TimesTable(){
    Column (modifier = Modifier.background(color = Color.Yellow)
                                //.padding(all = 16.dp)
                                .fillMaxSize())
        {
        for(i in 1 ..9){
            Row(modifier = Modifier
                .fillMaxWidth()
                .weight(1f)

            ){
                for(j in 1 ..9){

                    val color = if( (i + j).mod(2) == 1) Color.White else Color.Yellow
                    Box(modifier = Modifier.fillMaxHeight()
                        .weight(1f)
                        .border(width = 1.dp, color = Color.Gray)
                        .background(color = color),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "${i * j}")
                    }
                }
            }
        }
    }
}