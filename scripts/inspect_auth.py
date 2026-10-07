import os, glob, subprocess

gradle_cache = os.path.expanduser("~/.gradle/caches/modules-2/files-2.1/net.minestom")
jars = [j for j in glob.glob(os.path.join(gradle_cache, "**", "*.jar"), recursive=True) if "sources" not in j]
if jars:
    jar = jars[0]
    for cls in ["net.minestom.server.Auth$Online", "net.minestom.server.Auth$Offline"]:
        print(f"--- {cls} ---")
        res = subprocess.run(["javap", "-cp", jar, cls], capture_output=True, text=True)
        print(res.stdout)
