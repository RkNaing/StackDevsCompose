package com.rahulkumar.stackdevs.ui.screens.devs_list

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.rahulkumar.stackdevs.R
import com.rahulkumar.stackdevs.data.models.Badge
import com.rahulkumar.stackdevs.data.models.Dev
import com.rahulkumar.stackdevs.ui.theme.BronzeOrange
import com.rahulkumar.stackdevs.ui.theme.GoldYellow
import com.rahulkumar.stackdevs.ui.theme.SilverBrown
import com.rahulkumar.stackdevs.ui.theme.StackDevsTheme
import com.rahulkumar.stackdevs.utils.kotlin.compactRepresentation

@Composable
fun ItemDeveloper(
    modifier: Modifier = Modifier,
    dev: Dev,
    onClickProfileBtn: (String?) -> Unit,
    onClickPortfolioBtn: (String?) -> Unit
) {
    Card(
        modifier = modifier.fillMaxWidth()
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(dev.profileImage)
                        .crossfade(true)
                        .transformations(CircleCropTransformation())
                        .build(),
                    contentDescription = stringResource(id = R.string.content_desc_dev_profile_image),
                    placeholder = painterResource(id = R.drawable.img_placeholder_profile),
                    error = painterResource(id = R.drawable.img_placeholder_profile),
                    fallback = painterResource(id = R.drawable.img_placeholder_profile),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                        .border(
                            width = 2.dp,
                            color = MaterialTheme.colors.secondary,
                            shape = CircleShape
                        )
                        .padding(4.dp)
                )

                Column(
                    modifier = Modifier
                        .weight(1f, fill = true),
                    verticalArrangement = Arrangement.spacedBy(
                        3.dp,
                        Alignment.CenterVertically
                    ),
                ) {

                    dev.name.takeIf { !it.isNullOrBlank() }?.let { name ->
                        Text(
                            text = name,
                            Modifier.fillMaxWidth(),
                            style = MaterialTheme.typography.body1
                        )
                    }

                    Text(
                        text = stringResource(
                            id = R.string.lbl_reputation,
                            dev.reputationScore.compactRepresentation
                        ),
                        Modifier.fillMaxWidth(),
                        style = MaterialTheme.typography.subtitle2
                    )

                    dev.location.takeIf { !it.isNullOrBlank() }?.let { location ->
                        Text(
                            text = location,
                            Modifier.fillMaxWidth(),
                            style = MaterialTheme.typography.caption
                        )
                    }
                }
            }

            dev.badge.takeIf { it?.isEmpty?.not() ?: false }?.let { badge ->

                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                )

                Text(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    text = stringResource(id = R.string.lbl_badges),
                    style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.Bold),
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    BadgeView(
                        modifier = Modifier.weight(1F, fill = true),
                        color = BronzeOrange,
                        label = R.string.lbl_bronze,
                        count = badge.bronze
                    )

                    BadgeView(
                        modifier = Modifier.weight(1F, fill = true),
                        color = SilverBrown,
                        label = R.string.lbl_silver,
                        count = badge.silver
                    )

                    BadgeView(
                        modifier = Modifier.weight(1F, fill = true),
                        color = GoldYellow,
                        label = R.string.lbl_gold,
                        count = badge.gold
                    )
                }

            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                OutlinedButton(
                    modifier = Modifier.weight(1F, fill = true),
                    enabled = !dev.profileLink.isNullOrBlank(),
                    onClick = { onClickProfileBtn(dev.profileLink) }) {

                    Icon(
                        painter = painterResource(id = R.drawable.ic_stack_overflow),
                        contentDescription = stringResource(id = R.string.content_desc_icon_stackoverflow),
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(text = stringResource(id = R.string.lbl_profile))

                }

                OutlinedButton(
                    modifier = Modifier.weight(1F, fill = true),
                    enabled = !dev.portfolioLink.isNullOrBlank(),
                    onClick = { onClickPortfolioBtn(dev.portfolioLink) }) {

                    Icon(
                        painter = painterResource(id = R.drawable.ic_web),
                        contentDescription = stringResource(id = R.string.content_desc_icon_website),
//                        tint = MaterialTheme.colors.secondary
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(text = stringResource(id = R.string.lbl_web))

                }

            }

        }

    }
}

@Composable
private fun BadgeView(
    modifier: Modifier = Modifier,
    color: Color,
    @StringRes label: Int,
    count: Int
) {
    Column(
        modifier = modifier
            .border(
                width = 2.dp,
                color = color,
                shape = RoundedCornerShape(size = 4.dp)
            )
            .padding(4.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = count.toString(),
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = color,
                    shape = RoundedCornerShape(
                        topStart = 4.dp,
                        topEnd = 4.dp
                    )
                )
                .padding(vertical = 4.dp),
            color = Color.White,
            style = MaterialTheme.typography.body2.copy(fontWeight = FontWeight.Bold),
            textAlign = TextAlign.Center
        )

        Image(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1F)
                .padding(16.dp),
            painter = painterResource(id = R.drawable.ic_badge),
            contentDescription = "Badge Icon Silver",
            colorFilter = ColorFilter.tint(color = color)
        )

        Text(
            text = stringResource(id = label),
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = color,
                    shape = RoundedCornerShape(
                        bottomStart = 4.dp,
                        bottomEnd = 4.dp
                    )
                )
                .padding(vertical = 4.dp),
            color = Color.White,
            style = MaterialTheme.typography.caption,
            textAlign = TextAlign.Center
        )
    }
}

///////////////////////////////////////////////////////////////////////////
// Previews
///////////////////////////////////////////////////////////////////////////
@Preview(name = "Light Mode", uiMode = UI_MODE_NIGHT_NO)
@Preview(name = "Dark Mode", uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun ItemDeveloperPreview() {
    StackDevsTheme {
        ItemDeveloper(
            dev = Dev(
                name = "Rahul Kumar",
                portfolioLink = null,
                profileLink = "https://stackoverflow.rahul.com",
                location = "Singapore, Singapore",
                reputationScore = 25_258_357,
                badge = Badge(
                    bronze = 250_00,
                    gold = 295_14,
                    silver = 196_321
                )
            ),
            onClickPortfolioBtn = { /* NO-OP */ },
            onClickProfileBtn = { /*NO-OP*/ }
        )
    }
}

@Preview
@Composable
private fun BadgeViewPreview() {
    BadgeView(color = BronzeOrange, label = R.string.lbl_bronze, count = 58965)
}