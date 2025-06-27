package com.stslex.atten.feature.details.navigation

//fun NavGraphBuilder.detailsGraph() {
//    navScreen<Component.DetailScreen> { args ->
//        val store = getStore<DetailsStore>()
//
//        DisposableEffect(Unit) {
//            store.dispatch(Action.Init(args.id))
//            onDispose {
//                store.dispatch(Action.OnScreenLeft)
//            }
//        }
//
//        val state by remember(store) { store.state }.collectAsState()
//
//        DetailsScreen(
//            state = state,
//            onTitleChange = { store.dispatch(Action.OnTitleValueChanged(it)) },
//            onDescriptionChange = { store.dispatch(Action.OnDescriptionValueChanged(it)) },
//            onSaveClicked = { store.dispatch(Action.OnSaveClicked) }
//        )
//    }
//}
