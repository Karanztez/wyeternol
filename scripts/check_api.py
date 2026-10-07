import os
import glob
import subprocess

gradle_cache = os.path.expanduser("~/.gradle/caches/modules-2/files-2.1/net.minestom")
jars = [j for j in glob.glob(os.path.join(gradle_cache, "**", "*.jar"), recursive=True) if "sources" not in j]

if jars:
    jar = jars[0]
    print(f"[inspect] Inspecting: {jar}")
    res = subprocess.run(["javap", "-cp", jar, "net.minestom.server.Auth"], capture_output=True, text=True)
    print(res.stdout)
