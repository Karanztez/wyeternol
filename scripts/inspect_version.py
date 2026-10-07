import os, glob, subprocess

gradle_cache = os.path.expanduser("~/.gradle/caches/modules-2/files-2.1/net.minestom")
jars = [j for j in glob.glob(os.path.join(gradle_cache, "**", "*.jar"), recursive=True) if "sources" not in j]
if jars:
    jar = jars[0]
    res = subprocess.run(["javap", "-cp", jar, "-constants", "net.minestom.server.MinecraftServer"], capture_output=True, text=True)
    for line in res.stdout.splitlines():
        if "VERSION" in line:
            print(line)
