package com.example.library.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.KeyboardArrowLeft
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material.icons.rounded.Send
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.library.R

@Composable
fun DetailsScreen(navController: NavController, place: Place) {
    // Оборачиваем содержимое в Box, чтобы кнопка всегда была внизу
    Box(modifier = Modifier.fillMaxSize()) {
        // Прокручиваемый контент
        Column(
            modifier = Modifier
                .padding(15.dp)
                .verticalScroll(rememberScrollState()) // Это делает контент прокручиваемым
        ) {
            ImageHead(place, navController)
            HorizontalScroll()
            AdditionalInfo(place)
            Text(
                text = place.description,
                modifier = Modifier.padding(top = 15.dp, bottom = 50.dp),
                fontFamily = FontFamily(Font(R.font.roboto, FontWeight.Normal)),
                fontSize = 18.sp,
                color = Color(0xFFA5A5A5)
            )
        }

        // Кнопка внизу экрана
        Button(
            onClick = { },
            modifier = Modifier
                .padding(12.dp)
                .height(50.dp)
                .align(Alignment.BottomCenter), // Это фиксирует кнопку внизу
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF1B1B1B),
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier.weight(4f),
                    text = "Забронировать сейчас",
                    fontFamily = FontFamily(Font(R.font.roboto, FontWeight.Normal)),
                    fontSize = 20.sp
                )
                Icon(
                    modifier = Modifier.weight(1f),
                    imageVector = Icons.Rounded.Send,
                    contentDescription = "Забронировать",
                    tint = Color.White // Цвет иконки
                )
            }
        }
    }
}

@Composable
fun ImageHead(place: Place, navController: NavController) {
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp), // Тень
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) { // Оборачиваем в Box для позиционирования
            // Фото места
            Image(
                painter = painterResource(id = place.imageLink), // Замените на ваше изображение
                contentDescription = "Фон",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop // Масштабирование изображения
            )

            // Иконка "Добавить в избранное"
            var isFavorite by remember { mutableStateOf(place.isFavorite) }

            BookmarkIconButton(isFavorite = isFavorite) {
                isFavorite = !isFavorite
            }

            BackIconButton(navController)

            val robotoFont = FontFamily(Font(R.font.roboto, FontWeight.Normal))

            val interFont = FontFamily(
                Font(R.font.inter, FontWeight.Normal)
            )

            // Бокс, который должен быть прижат к низу
            Box(
                modifier = Modifier
                    .fillMaxWidth() // Растягиваем по ширине
                    .height(120.dp) // Высота блока
                    .align(Alignment.BottomCenter) // Прижимаем к низу и центру
                    .padding(16.dp) // Отступы внутри бокса
                    .background(
                        Color.Black.copy(alpha = 0.5f), // Прозрачный фон (40%)
                        shape = RoundedCornerShape(16.dp) // Скругленные углы
                    )
            ) {
                Column(modifier = Modifier.padding(start = 16.dp, bottom = 16.dp).align(Alignment.BottomStart)) {
                    Text(
                        text = place.place,
                        fontFamily = interFont,
                        fontSize = 24.sp,
                        color = Color.White
                    )
                    Row {
                        Icon(
                            imageVector = Icons.Rounded.LocationOn,
                            contentDescription = "Местоположение",
                            tint = colorResource(R.color.pale_grey) // Цвет иконки
                        )
                        Text(
                            text = place.country,
                            fontFamily = robotoFont,
                            fontSize = 18.sp,
                            color = colorResource(R.color.pale_grey)
                        )
                    }
                }

                Column(modifier = Modifier.padding(end = 16.dp, bottom = 16.dp).align(Alignment.BottomEnd)) {
                    Text(
                        text = "Цена",
                        color = colorResource(R.color.pale_grey),
                        fontFamily = robotoFont,
                        fontSize = 16.sp,
                        modifier = Modifier.align(Alignment.End)
                    )
                    Text(
                        text = "$${place.price}",
                        color = colorResource(R.color.pale_grey),
                        fontFamily = robotoFont,
                        fontSize = 20.sp,
                        modifier = Modifier.align(Alignment.End)
                    )
                }
            }
        }
    }
}

@Composable
fun BookmarkIconButton(isFavorite: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(40.dp) // Размер фона
                .background(
                    color = colorResource(R.color.background_grey).copy(alpha = 0.4f), // Цвет фона
                    shape = CircleShape // Круглая форма
                )
                .padding(8.dp) // Отступ внутри фона
                .align(Alignment.TopEnd)
        ) {
            IconButton(onClick = onClick) {
                Icon(
                    imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                    contentDescription = "Избранное",
                    tint = if (isFavorite) Color.Red else Color.White // Цвет иконки
                )
            }
        }
    }
}

@Composable
fun BackIconButton(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(40.dp) // Размер фона
                .background(
                    color = colorResource(R.color.background_grey).copy(alpha = 0.4f), // Цвет фона
                    shape = CircleShape // Круглая форма
                )
                .padding(8.dp) // Отступ внутри фона
                .align(Alignment.TopStart)
        ) {
            IconButton(onClick = { navController.navigate("main_screen") }) {
                Icon(
                    imageVector = Icons.Outlined.KeyboardArrowLeft,
                    contentDescription = "Назад",
                    tint = Color.White // Цвет иконки
                )
            }
        }
    }
}

@Composable
fun HorizontalScroll() {
    // Состояние, которое отслеживает активную кнопку
    var selectedButton by remember { mutableStateOf(0) }

    // Создаем состояние прокрутки
    val scrollState = rememberScrollState()

    // Оборачиваем Row в horizontalScroll
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(scrollState), // Включаем горизонтальную прокрутку
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Кнопки
        Button(
            selectedButton = selectedButton,
            buttonIndex = 0,
            onClick = { selectedButton = 0 },
            text = "Обзор"
        )

        Button(
            selectedButton = selectedButton,
            buttonIndex = 1,
            onClick = { selectedButton = 1 },
            text = "Подробности"
        )
    }
}

@Composable
fun Button(
    selectedButton: Int,
    buttonIndex: Int,
    onClick: () -> Unit,
    text: String
) {
    val interFont = FontFamily(
        Font(R.font.inter, FontWeight.Normal)
    )
    Button(
        onClick = onClick,
        modifier = Modifier.padding(4.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = if (selectedButton == buttonIndex) colorResource(R.color.dark_grey) else colorResource(
                R.color.light_grey
            ),
        )
    ) {
        Text(
            text = text,
            fontFamily = interFont,
            fontSize = returnSp(selectedButton == buttonIndex).sp
        )
    }
}

fun returnSp(selectedButton: Boolean): Int {
    return if (selectedButton) 22
    else 16
}

@Composable
fun AdditionalInfo(place: Place) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = Modifier.weight(1f)) {
            IconText(ImageBitmap.imageResource(R.drawable.recent_actions), "${place.duration} ч")
        }
        Box(modifier = Modifier.weight(1f)) {
            IconText(ImageBitmap.imageResource(R.drawable.cloud), "${place.temperature} °C")
        }
        Box(modifier = Modifier.weight(1f)) {
            IconText(ImageBitmap.imageResource(R.drawable.star), place.rating.toString())
        }
    }
}

@Composable
fun IconText(icon: ImageBitmap, text: String) {
    val robotoFont = FontFamily(Font(R.font.roboto, FontWeight.Normal))
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(40.dp) // Размер фона
                .background(
                    color = Color(0xFFEDEDED), // Цвет фона
                    shape = RoundedCornerShape(12.dp) // Круглая форма
                )
                .padding(8.dp) // Отступ внутри фона
        ) {
            Image(
                bitmap = icon,
                contentDescription = "icon",
                Modifier
                    .height(50.dp)
                    .width(50.dp),
                colorFilter = ColorFilter.tint(Color(0xFF3F3F3F))
            )
        }
        Text(
            text = text,
            color = Color(0xFF7E7E7E),
            fontSize = 18.sp,
            fontFamily = robotoFont,
            modifier = Modifier.padding(start = 5.dp)
        )
    }
}