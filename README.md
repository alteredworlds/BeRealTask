# BeRealTask

This solution does not cover all bonus steps. A simple LoginScreen is included.

Focus was on multi-module architecture combined with Compose and Compose Navigation.

The UI (& Compose code in general) could be greatly improved: for example, there is no TopBar providing back navigation, system back navigation must be used instead.

Images are shown using Glide, a library I have used in the past and found to be very configurable. The main related change can be found in ```com.tomgilbert.core.glide.FileItemModelLoader```

Unit and Integration tests are included, but do not provide comprehensive coverage. Although I believe everything included has been architected so as to be testable, I ran out of time!

I have spent more than 4 hours on this task, but have been very much enjoying using it as a chance to learn about Compose Navigation and multi-module setup. Next time around I'd use Kotlin DSL rather than Gradle (see Google's 'nowinandroid' sample).


