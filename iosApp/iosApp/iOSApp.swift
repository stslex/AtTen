import SwiftUI
import commonApp

@main
struct iOSApp: App {

    init() {
        InitKoinKt.InitKoin()
    }

	var body: some Scene {
		WindowGroup {
			ContentView()
		}
	}
}