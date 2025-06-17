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

#2.4 Instagram Profile Card. Создание шаблона

@Preview
@Composable
fun InstagramProfileCard(){

    Row(modifier = Modifier.fillMaxWidth()
        .height(500.dp)
        .padding(all = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly, <---- распределение равномерное по горизонали
		verticalAlignment = Alignment.CenterVertically ) <---- выравнивание элементов по вертикали			
		{ 
      Box(modifier = Modifier
          .size(50.dp)
          .background(color = Color.Yellow)
      ){}
        Column(modifier = Modifier
            .height(80.dp)
            .background(color = Color.Green),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly <---- распределение равномерное по вертикали
        ){
            Box(modifier = Modifier
                .size(25.dp)
                .background(color = Color.Blue)
            ){}
            Box(modifier = Modifier
                .size(25.dp)
                .background(color = Color.Red)
            ){}
        }

    }
}

Но колонок Column должно быть 3 - вынесем её в отдельную функцию TwoBoxes()


@Preview
@Composable
fun InstagramProfileCard(){

    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(all = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically ){
      Box(modifier = Modifier
          .size(50.dp)
          .background(color = Color.Yellow)
      ){}
        TwoBoxes()
        TwoBoxes()
        TwoBoxes()

    }
}

@Composable
private  fun TwoBoxes(){
    Column(modifier = Modifier
        .height(80.dp)
        .background(color = Color.Green),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ){
        Box(modifier = Modifier
            .size(25.dp)
            .background(color = Color.Blue)
        ){}
        Box(modifier = Modifier
            .size(25.dp)
            .background(color = Color.Red)
        ){}
    }
}

#2.5 Card

Сделаем аналог CardView

Потестируем Card

@Preview
@Composable
fun CardTest(){
    Card(modifier = Modifier.padding(16.dp),
        shape = RoundedCornerShape(4.dp).copy(
            bottomStart = CornerSize(0.dp), <-- скруугление только в верхних углах
            bottomEnd = CornerSize(0.dp))
    ){
        Text("Hello World" , modifier = Modifier.padding(16.dp))
    }
}

Либо так задать углы

@Preview
@Composable
fun CardTest(){
    Card(modifier = Modifier.padding(16.dp),
        shape = RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp)
    ){
        Text("Hello World" , modifier = Modifier.padding(16.dp))
    }
}

@Preview
@Composable
fun CardTest(){
    Card(modifier = Modifier.padding(16.dp),
        shape = RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Green, contentColor = Color.Red) <--- цвет фона и цвет контента
    ){
        Text("Hello World" , modifier = Modifier.padding(16.dp))
    }
}

Добавляем Card в InstagramProfileCard

@Preview
@Composable
fun InstagramProfileCard(){

    Card (shape = RoundedCornerShape(topStart =8.dp, topEnd = 8.dp),
        border = BorderStroke(width = 1.dp, color = Color.Black),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    )
    {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(all = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically ){
            Box(modifier = Modifier
                .size(50.dp)
                .background(color = Color.Yellow)
            ){}
            TwoBoxes()
            TwoBoxes()
            TwoBoxes()

        }
    }

}