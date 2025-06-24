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

#2.6 Dark-Light Themes
В файле Theme.kt есть функция TestComposeTheme, которая определяет Dark или Light тему в зависимости от системных настроек. А цвета в темах можно настроить
Это Composable-функция. Поэтому мы её добавляем в активити

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //enableEdgeToEdge()
        setContent {
            TestComposeTheme {
                InstagramProfileCard()
            }

       }
    }
}

Зададим цвета для тем

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80,

    background = Color.Black,
    surface = Color.Black,
    onPrimary = Color.Black,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color.White,
    onSurface = Color.White,
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40,

    
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onTertiary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    
)

Поменяем цвета в карточке

fun InstagramProfileCard(){

    Card (shape = RoundedCornerShape(topStart =8.dp, topEnd = 8.dp),
        border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.onBackground),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
    )
	....
	
И сделаем два превью для разных схем

@Preview
@Composable
fun InstagramProfileCardLight(){
    TestComposeTheme(darkTheme = false){
        InstagramProfileCard()
    }
}

@Preview
@Composable
fun InstagramProfileCardDark(){
    TestComposeTheme(darkTheme = true){
        InstagramProfileCard()
    }
}

Поменяем также цвет за пределами карточки

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //enableEdgeToEdge()
        setContent {
            TestComposeTheme {
                Column (modifier = Modifier
                    .fillMaxSize()
                    .background(color = MaterialTheme.colorScheme.background)
                ){
                    InstagramProfileCard()
                }

            }

       }
    }
}
Но в таких случаях использование Column излишне, лучше использовать Box


#2.7 Text

@Composable
fun Text(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    fontSize: TextUnit = TextUnit.Unspecified, <---- Наследуется от родительской функции
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified, <---- Наследуется от родительской функции
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified, <---- Наследуется от родительской функции
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    style: TextStyle = LocalTextStyle.current
) {

Поработаем с параметрами

Text(text ="Hello world",
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        fontStyle = FontStyle.Italic,
        fontFamily = FontFamily.Serif,
        textDecoration = TextDecoration.Underline + TextDecoration.LineThrough
    )
	
Если в строке нужно разные части декорировать по разному используем

@Preview
@Composable
fun TestText(){

    Text(
        buildAnnotatedString {
            withStyle(SpanStyle(fontWeight = FontWeight.Bold)){ <--- стиль для отдельной части строки
                append("Hello")
            }
            withStyle(SpanStyle(textDecoration = TextDecoration.Underline)){
                append(" ")
            }
            withStyle(SpanStyle(fontSize = 30.sp, textDecoration = TextDecoration.LineThrough)){
                append("World")
                append("!")
            }

        }
    )
}

Вернёмся к InstagramProfileCard

fun InstagramProfileCard(){

    Card (modifier = Modifier.padding(8.dp),
        shape = RoundedCornerShape(topStart =8.dp, topEnd = 8.dp),
        border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.onBackground),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
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
            UserStatistic(title = "Posts", value = "9849") <--- передаём параметры пока так
            UserStatistic(title = "Followers", value = "576")
            UserStatistic(title = "Following", value = "900")

        }
    }

}
 //Переименовали TwoBoxes 
@Composable
private  fun UserStatistic(title : String, value : String){ <--- добавили параметры
    Column(modifier = Modifier
        .height(80.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ){
        Text(text = value, <----  Box заменили на Text
            fontSize = 30.sp,
            fontFamily = FontFamily.Cursive
            )
        Text( text = title,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold)
    }
}

#2.8 Image


@Preview
@Composable
fun TestImage(){

    Image(modifier = Modifier.clip(shape = CircleShape), <--- картинка вставляется в круг
        painter = painterResource(id = R.drawable.ic_launcher_background),
        contentDescription = "",
        contentScale = ContentScale.FillBounds)
}

@Composable
fun InstagramProfileCard(){

    Card (modifier = Modifier.padding(8.dp),
        shape = RoundedCornerShape(topStart =8.dp, topEnd = 8.dp),
        border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.onBackground),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
    )
    {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(all = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically ){
            Image(modifier = Modifier
                .size(50.dp),
                //.clip(shape = CircleShape),
                painter = painterResource(id = R.drawable.ic_instagram),
                contentDescription = "",

            )
            UserStatistic(title = "Posts", value = "9849")
            UserStatistic(title = "Followers", value = "576")
            UserStatistic(title = "Following", value = "900")

        }
    }

}

#2.9 Порядок modifier-ов

Сделаем следующее

@Preview
@Composable
fun TestImage(){

    Box(modifier = Modifier.size(200.dp)
        .background(Color.Cyan)){
        Image(modifier = Modifier.size(100.dp),
            painter = ColorPainter(Color.Yellow),
            contentDescription = "",
            contentScale = ContentScale.FillBounds)
    }

}


@Preview
@Composable
fun TestImage(){

    Box(modifier = Modifier.size(200.dp)
        .background(Color.Cyan)){
        Image(modifier = Modifier.size(100.dp)
            .background(Color.Red)
            .padding(25.dp),
            painter = ColorPainter(Color.Yellow),
            contentDescription = "",
            contentScale = ContentScale.FillBounds)
    }

}
<--- видим голубой фон. Box красный и внутри жёлтый квадрат

Теперь поменяем padding и size

@Preview
@Composable
fun TestImage(){

    Box(modifier = Modifier.size(200.dp)
        .background(Color.Cyan)){

        Image(modifier = Modifier
            .background(Color.Green)
            .padding(25.dp)
            .size(100.dp)
            .background(Color.Red).padding(25.dp),
            painter = ColorPainter(Color.Yellow),
            contentDescription = "",
            contentScale = ContentScale.FillBounds)
    }

}

Порядок модификаторов имеет значение и прменяется по порядку. + модифайеры можно повторять

Сделаем иконку Instagram в белом круге для любой темы
@Composable
fun InstagramProfileCard(){

    Card (modifier = Modifier.padding(8.dp),
        shape = RoundedCornerShape(topStart =8.dp, topEnd = 8.dp),
        border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.onBackground),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
    )
    {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(all = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically ){
            Image(modifier = Modifier
                .clip(shape = CircleShape)
                .background(color = Color.White)
                .padding(8.dp)
                .size(50.dp),
                painter = painterResource(id = R.drawable.ic_instagram),
                contentDescription = "",

            )
            UserStatistic(title = "Posts", value = "9849")
            UserStatistic(title = "Followers", value = "576")
            UserStatistic(title = "Following", value = "900")

        }
    }

}

#2.10 Завершение работы над InstagramProfileCard


@Composable
fun InstagramProfileCard(){

    Card (modifier = Modifier.padding(8.dp),
        shape = RoundedCornerShape(topStart =8.dp, topEnd = 8.dp),
        border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.onBackground),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
    )
    {
        Column (modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
        ) {
            Row(modifier = Modifier
                .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically ){
                Image(modifier = Modifier
                    .clip(shape = CircleShape)
                    .background(color = Color.White)
                    .padding(8.dp)
                    .size(50.dp),
                    painter = painterResource(id = R.drawable.ic_instagram),
                    contentDescription = "",

                    )
                UserStatistic(title = "Posts", value = "9849")
                UserStatistic(title = "Followers", value = "576")
                UserStatistic(title = "Following", value = "900")

            }
			<----- Добавили 3 текста
            Text(text = "Instagram",
                fontSize = 24.sp,
                fontStyle = FontStyle.Italic,
                fontFamily = FontFamily.Cursive
            )
            Text(text = "#YoursToMake",
                fontSize = 16.sp)
            Text(text = "www.facebook.com//",
                fontSize = 16.sp)

            Spacer(modifier = Modifier.height(16.dp))
			<-------- Добавили кнопку с текстом
            Button(onClick = { }) {
                Text(text = "Follow")
            }
        }

    }

}

#4.2 Добавление VIewModel в InstagramProfileCard

Добавим стейт для кнопки и будем по нему менять текст кнопки

fun InstagramProfileCard(){
    val isFollowed = remember {
        mutableStateOf(false)
    }
	.....

  Button(onClick = { isFollowed.value = !isFollowed.value }) {
                Text(text = if(isFollowed.value) "Unfollow" else "Follow")
            }
При повороте состояние не сохраняется

remember - умеет переживать рекомпозицию, но не умеет переживать поворот экрана. Чтобы это исправить нужно использовать rememberSaveable

 val isFollowed = rememberSaveable {
        mutableStateOf(false)
    }
	
Поменяем также цвет кнопки
Button(onClick = { isFollowed.value = !isFollowed.value },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if(isFollowed.value) {
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
                    } else{
                        MaterialTheme.colorScheme.primary
                    }
                )) {
                Text(text = if(isFollowed.value) "Unfollow" else "Follow")
            }
Сейчас всё хорошо и всё работает. НО КНОПКА САМА УПРАВЛЯЕТ своим состоянием. Это не очень хорошее поведение. Это STATEFULL Composable-функция

Сделаем некоторые изменения. Во первых вынесем кнопку в отдельную функцию


@Composable
private fun FollowButton(isFollowed  : Boolean, clickListener: () -> Unit){ <--- передаём значение Boolean и функцию, которая не принимает никаких значений и ничего не возвращает
    Button(onClick = {
                        clickListener() <---- просто вызов функции
                     },
        colors = ButtonDefaults.buttonColors(
            containerColor = if(isFollowed) {
                MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
            } else{
                MaterialTheme.colorScheme.primary
            }
        )) {
        Text(text = if(isFollowed) "Unfollow" else "Follow")
    }
}

МЫ ПОЛУЧИЛИ кнопку, которая внутри себя не хранит никакой стейт и также его не изменяет. Это называется STATELESS Composable-функция

Вызываем так

 FollowButton(isFollowed = isFollowed.value){
                isFollowed.value = !isFollowed.value
            }
			
Сейчас мы ещё улучшили приложение, но этого также не достаточно!
Сейчас мы прямо здесь опеределяем пользователь подписан или нет. Но это не логика View-слоя
Вынесем её в ViewModel


class MainViewModel : ViewModel() {
    
    private val _isFollowing = MutableLiveData<Boolean>(false)
    val isFollowing : LiveData<Boolean> = _isFollowing
    
    fun changeFollowingStatus(){
        val value = _isFollowing.value ?: false
        _isFollowing.value = !value
    }
}

Добавляем в активити и передаём в InstagramProfileCard

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        //enableEdgeToEdge()
        setContent {
            TestComposeTheme {
                Box (modifier = Modifier
                    .fillMaxSize()
                    .background(color = MaterialTheme.colorScheme.background)
                ){
                    InstagramProfileCard(viewModel)
                }

            }

       }
    }
}

Но Compose не умеет работаь с LiveData. Нужно добавлять доп.библиотеку

implementation("androidx.compose.runtime:runtime-livedata:1.2.1")

И далее 

@Composable
fun InstagramProfileCard(mainViewModel: MainViewModel){
    val isFollowed=  mainViewModel.isFollowing.observeAsState(initial = false)
	............
	и по клику вызываем функцию из VIewModel
	
         FollowButton(isFollowed = isFollowed.value){
                mainViewModel.changeFollowingStatus()
            }
	.........
	
#4.3 Делегаты

Предположим есть состояние в Composable

  var a : MutableState<Int> = remember {
        mutableStateOf(5)
    }
    
    val b : Int = a.value <--- Это получение значения
    a.value = 10 <----- Это запись значения
	
	Теперь сделаем небольшое изменение вместо = сделаем by <---- Делегирование
	
	 var a : MutableState<Int> by remember {
        mutableStateOf(5)
    }
	
	При этом импортируются 
	
	
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

А переменная a уже будет типа Int

 var a : Int by remember {
        mutableStateOf(5)
    }
И вытаскивать/менять значения уже можно без value, при этом вызываются делегаты getValue/setValue , которые требовалось импортировать выше!
    val b = a
    a = 10
	
Таким образом можно изменить и STATE

    val isFollowed =  mainViewModel.isFollowing.observeAsState(initial = false)
			на
	val isFollowed by  mainViewModel.isFollowing.observeAsState(initial = false) <---- Тип уже Boolean
	
	
Добавим логи

@Composable
fun InstagramProfileCard(mainViewModel: MainViewModel){
    Log.d("RECOMPOSITION", "InstagramProfileCard") <------------------------------
    val isFollowed by  mainViewModel.isFollowing.observeAsState(initial = false)

    Card (modifier = Modifier.padding(8.dp),
        shape = RoundedCornerShape(topStart =8.dp, topEnd = 8.dp),
        border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.onBackground),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
    )
    {
        Log.d("RECOMPOSITION", "Card") <------------------------------
        Column (modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
        ) {
            Row(modifier = Modifier
                .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically ){
                Image(modifier = Modifier
                    .clip(shape = CircleShape)
                    .background(color = Color.White)
                    .padding(8.dp)
                    .size(50.dp),
                    painter = painterResource(id = R.drawable.ic_instagram),
                    contentDescription = "",

                    )
                UserStatistic(title = "Posts", value = "9849")
                UserStatistic(title = "Followers", value = "576")
                UserStatistic(title = "Following", value = "900")

            }
            Text(text = "Instagram",
                fontSize = 24.sp,
                fontStyle = FontStyle.Italic,
                fontFamily = FontFamily.Cursive
            )
            Text(text = "#YoursToMake",
                fontSize = 16.sp)
            Text(text = "www.facebook.com//",
                fontSize = 16.sp)

            Spacer(modifier = Modifier.height(16.dp))
            FollowButton(isFollowed = isFollowed){ <-------------- Стейт получаем ещё в функции Card
                mainViewModel.changeFollowingStatus()
            }
        }

    }

}
@Composable
private fun FollowButton(isFollowed  : Boolean, clickListener: () -> Unit){

    Log.d("RECOMPOSITION", "FollowButton") <------------------------------
    Button(onClick = {
                        clickListener()
                     },
        colors = ButtonDefaults.buttonColors(
            containerColor = if(isFollowed) {
                MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
            } else{
                MaterialTheme.colorScheme.primary
            }
        )) {
        Text(text = if(isFollowed) "Unfollow" else "Follow")
    }
}

При вызове и нажатии на кнопку результат следующий

2025-06-20 12:45:37.641 29906-29906 RECOMPOSITION           ru.melolchik.testcompose             D  InstagramProfileCard
2025-06-20 12:45:37.663 29906-29906 RECOMPOSITION           ru.melolchik.testcompose             D  Card
2025-06-20 12:45:37.728 29906-29906 RECOMPOSITION           ru.melolchik.testcompose             D  FollowButton
2025-06-20 12:45:44.966 29906-29906 RECOMPOSITION           ru.melolchik.testcompose             D  Card
2025-06-20 12:45:44.971 29906-29906 RECOMPOSITION           ru.melolchik.testcompose             D  FollowButton
2025-06-20 12:45:51.015 29906-29906 RECOMPOSITION           ru.melolchik.testcompose             D  Card
2025-06-20 12:45:51.044 29906-29906 RECOMPOSITION           ru.melolchik.testcompose             D  FollowButton
2025-06-20 12:45:52.532 29906-29906 RECOMPOSITION           ru.melolchik.testcompose             D  Card
2025-06-20 12:45:52.542 29906-29906 RECOMPOSITION           ru.melolchik.testcompose             D  FollowButton
2025-06-20 12:45:54.338 29906-29906 RECOMPOSITION           ru.melolchik.testcompose             D  Card
2025-06-20 12:45:54.346 29906-29906 RECOMPOSITION           ru.melolchik.testcompose             D  FollowButton
2025-06-20 12:45:55.714 29906-29906 RECOMPOSITION           ru.melolchik.testcompose             D  Card
2025-06-20 12:45:55.721 29906-29906 RECOMPOSITION           ru.melolchik.testcompose             D  FollowButton

Т.е. вызывается рекомпозиция не только кнопки, но и все карточки. Почему - потому-что стейт для кнопки получаем ещё в функции Card!

Как же сделать так,чтобы при изменении кнопки перерисовывалась только кнопка!

Нужно передать в кнопку не Boolean а State<Boolean>

Возвращаем isFollowed в STATE

val isFollowed =  mainViewModel.isFollowing.observeAsState(initial = false) !!! РАВНО
 FollowButton(isFollowed = isFollowed){
                mainViewModel.changeFollowingStatus()
            }
			
@Composable
private fun FollowButton(isFollowed  : State<Boolean>, clickListener: () -> Unit){ <---- isFollowed  : State<Boolean>

    Log.d("RECOMPOSITION", "FollowButton")
    Button(onClick = {
                        clickListener()
                     },
        colors = ButtonDefaults.buttonColors(
            containerColor = if(isFollowed.value) { <----- через value
                MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
            } else{
                MaterialTheme.colorScheme.primary
            }
        )) {
        Text(text = if(isFollowed.value) "Unfollow" else "Follow") <----- через value
    }
}

Проверяем логи по рекомпозиции

2025-06-20 12:55:20.603  1835-1835  RECOMPOSITION           ru.melolchik.testcompose             D  InstagramProfileCard
2025-06-20 12:55:20.624  1835-1835  RECOMPOSITION           ru.melolchik.testcompose             D  Card
2025-06-20 12:55:20.686  1835-1835  RECOMPOSITION           ru.melolchik.testcompose             D  FollowButton
2025-06-20 12:55:40.932  1835-1835  RECOMPOSITION           ru.melolchik.testcompose             D  FollowButton
2025-06-20 12:55:41.609  1835-1835  RECOMPOSITION           ru.melolchik.testcompose             D  FollowButton
2025-06-20 12:55:42.065  1835-1835  RECOMPOSITION           ru.melolchik.testcompose             D  FollowButton
2025-06-20 12:55:42.263  1835-1835  RECOMPOSITION           ru.melolchik.testcompose             D  FollowButton
2025-06-20 12:55:42.417  1835-1835  RECOMPOSITION           ru.melolchik.testcompose             D  FollowButton

Лишней перерисовки не происходит, по этой причине использование делегатов не всегда удобно
Мы не зависм от стейта, пока не вызываем .value


#4.6 LazyColumn

Выведем несколько элементов InstagramProfileCard - список

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        //enableEdgeToEdge()
        setContent {
            Test(viewModel = viewModel)

       }
    }
}


@Composable
fun Test(viewModel: MainViewModel){
    TestComposeTheme {
        Box (modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
        ){
            Column { <------------ Column не скролится, это нужно добавить!
                repeat(100) {
                    InstagramProfileCard(viewModel)
                }
            }
            
        }

    }
}


@Composable
fun Test(viewModel: MainViewModel){
    TestComposeTheme {
        Box (modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
        ){
            val scrollState = rememberScrollState() <--- запоминаем и передаём state скрола
            Column(modifier = Modifier.verticalScroll(scrollState)) {
                repeat(100) {
                    InstagramProfileCard(viewModel)
                }
            }

        }

    }
}

Всё работает и скролится, но если добавить 500 элементов - всё будет тормозить - работает как ListView
Для этого был создан RecyclerView. Здесь аналог - LazyColumn

Но 

LazyColumn() { //content: LazyListScope.() -> Unit
                item { <---- используется для добавления любого элемента в список как контейнер
                    Text(text = "Text1")
                }
//                repeat(100) {
//                    InstagramProfileCard(viewModel)
//                }
            }

-----------------
 LazyColumn() {

                repeat(100) {

                    item {
                        Log.d("Test", "Item index = $it")
                        InstagramProfileCard(viewModel)
                    }

                }
            }
----- либо тоже самое так

 LazyColumn() {

                items(100) { <-----------------------
                    Log.d("Test", "Item index = $it")
                    InstagramProfileCard(viewModel)
                }
            }
			
При скроле список может подтормаживать - обычно это только на дебажный сборках!!