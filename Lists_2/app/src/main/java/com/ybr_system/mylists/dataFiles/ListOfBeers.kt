package com.ybr_system.mylists.dataFiles

internal var beers: MutableList<Beer> = mutableListOf(
    Beer.Lager(
        name = "San Miguel",
        image = "https://52brews.com/wp-content/uploads/2018/08/Beer-Mug-Foam-768x901.webp",
        type = "LAGER",
        country = "Philippines"
    ),
    Beer.Porter(
        name = "Hill Farmstead Everett",
        image = "https://thumbs.dreamstime.com/b/dark-porter-beer-glass-cup-refreshing-drink-white-foam-d-illustration-splashing-beer-dark-porter-beer-glass-cup-134040942.jpg",
        type = "PORTER",
        country = "US"
    ),
    Beer.Dunkel(
        name = "Kloster Kreuzberg Dunkel",
        image = "https://i1.wp.com/www.beerwanderers.com/wp-content/uploads/2020/03/IMG_5672.jpg?resize=416%2C409&ssl=1",
        type = "DUNKEL",
        country = "Germany"
    ),
    Beer.Stout(
        name = "Guinness",
        image = "https://52brews.com/wp-content/uploads/2018/08/Dark-Beer-Stout-Glass-277x300.webp",
        type = "STOUT",
        country = "Ireland"
    ),
    Beer.Cider(
        name = "Herrljunga Cider",
        image = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRZIKpVEyjJrnJeq-RlAHixoPIktOkxgMopWg&usqp=CAU",
        type = "CIDER",
        country = "Sweden"
    )
)