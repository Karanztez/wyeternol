import os, glob, subprocess, zipfile

gradle_cache = os.path.expanduser("~/.gradle/caches/modules-2/files-2.1/net.minestom")
jars = [j for j in glob.glob(os.path.join(gradle_cache, "**", "*.jar"), recursive=True) if "sources" not in j]
if jars:
    jar = jars[0]
    with zipfile.ZipFile(jar, 'r') as z:
        for name in z.namelist():
            if name.endswith(".class") and "version" in name.lower():
                print("Found class:", name)
                
    # Also javap on MinecraftServer
    res = subprocess.run(["javap", "-cp", jar, "net.minestom.server.MinecraftServer"], capture_output=True, text=True)
    for l in res.stdout.splitlines():
        if "version" in l.lower() or "protocol" in l.lower():
            print("MinecraftServer:", l)
