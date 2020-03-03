import au.com.mebank.HelloExtension

extensions.create<HelloExtension>("hello", project.objects)

tasks.register("hello", HelloTask::class)