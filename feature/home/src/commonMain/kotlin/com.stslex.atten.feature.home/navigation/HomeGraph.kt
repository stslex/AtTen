package com.stslex.atten.feature.home.navigation

//fun NavGraphBuilder.homeGraph() {
//    navScreen<Component.Home> {
//        val store = getStore<HomeStore>()
//        val state by remember { store.state }.collectAsState()
//        val hapticFeedback = LocalHapticFeedback.current
//
//        LaunchedEffect(Unit) {
//            store.dispatch(Action.Init)
//
//            store.event.collectLatest {
//                when (it) {
//                    is Event.Snackbar -> {
//                        // show snackbar
//                    }
//                }
//            }
//        }
// todo: add back handler
//        AppBackHandler(
//            enabled = state.selectedItems.isNotEmpty(),
//            onBack = {
//                store.dispatch(Action.OnBackPressed)
//            }
//        )

//        HomeScreen(
//            state = state,
//            onItemClicked = { id ->
//                hapticFeedback.performHapticFeedback(HapticFeedbackType.TextHandleMove)
//                store.dispatch(Action.OnItemClicked(id))
//            },
//            onLoadNext = {
//                store.dispatch(Action.LoadMore)
//            },
//            onCreateItemClick = {
//                hapticFeedback.performHapticFeedback(HapticFeedbackType.TextHandleMove)
//                store.dispatch(Action.OnCreateItemClicked)
//            },
//            onDeleteItemsClick = {
//                hapticFeedback.performHapticFeedback(HapticFeedbackType.TextHandleMove)
//                store.dispatch(Action.OnDeleteItemsClicked)
//            },
//            onItemLongCLick = { id ->
//                hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
//                store.dispatch(Action.OnSelectItemClicked(id))
//            }
//        )
//    }
//}
