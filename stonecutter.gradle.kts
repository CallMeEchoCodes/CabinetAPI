plugins {
    id("dev.kikugie.stonecutter")
}
stonecutter active "1.21" /* [SC] DO NOT EDIT */

stonecutter registerChiseled tasks.register("chiseledPublish", stonecutter.chiseled) {
    group = "publishing"
    ofTask("publish")
}

stonecutter registerChiseled tasks.register("chiseledBuild", stonecutter.chiseled) {
    group = "build"
    ofTask("build")
}