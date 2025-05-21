package com.example.testforrickmasters.presentation.componentsForScreens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.testforrickmasters.domain.model.UserDomain
import com.example.testforrickmasters.presentation.viewModels.StatisticsViewModel
import com.example.testforrickmasters.presentation.viewModels.VisitorViewModel

@Composable
fun MostViewersView(
    viewModel: VisitorViewModel = hiltViewModel()
) {

    val visitors by viewModel.visitors.collectAsState()

    Column(modifier = Modifier
        .width(344.dp)
        .height(222.dp)
        .padding(start = 16.dp, top = 12.dp)) {
        Text(
            text = "Чаще всего посещают Ваш профиль",
            modifier = Modifier.fillMaxWidth(),
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            maxLines = 1
        )
        Box(
            modifier = Modifier
                .width(344.dp)
                .height(186.dp)
                .background(color = Color.White, shape = RoundedCornerShape(20.dp))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(344.dp),
                verticalArrangement = Arrangement.SpaceEvenly
            )
            {
                visitors.take(3).forEach { visitor ->
                    VisitorPreview(visitor)
                }
            }
        }
    }
}

@Composable
fun VisitorPreview(visitor: UserDomain) {
    Box(
        modifier = Modifier
            .width(344.dp)
            .height(62.dp)
            .padding(top = 12.dp)

    ) {
        Row {
            AvatarWithOnlineStatus(
                imageUrl = visitor.avatarUrl.toString(),
                isOnline = visitor.isOnline,
            )
            Text(visitor.username + ", " + visitor.age)

        }
        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = "Стрелка вправо",
            tint = Color.Black,
            modifier = Modifier.size(24.dp).align(Alignment.TopEnd)
        )

    }
}

@Composable
fun AvatarWithOnlineStatus(
    imageUrl: String,
    isOnline: Boolean
) {
    Box(modifier = Modifier.padding(horizontal = 16.dp)) {

        AsyncImage(
            model = imageUrl,
            contentDescription = "User avatar",
            modifier = Modifier
                .size(38.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )

        if (isOnline) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(Color.Green)
                    .border(
                        width = 2.dp,
                        color = Color.White,
                        shape = CircleShape
                    )
            )
        }
    }
}