import subprocess

jar = "build/wyeternol-server.jar"
res = subprocess.run(["javap", "-cp", jar, "-constants", "net.minestom.server.MinecraftConstants"], capture_output=True, text=True)
print(res.stdout)

import zipfile
with zipfile.ZipFile(jar, 'r') as z:
    for name in z.namelist():
        if "ping" in name.lower() or "serverlist" in name.lower() or "status" in name.lower():
            if name.endswith(".class") and "minestom" in name:
                print("Found:", name)
