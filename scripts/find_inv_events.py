import zipfile

jar = "build/wyeternol-server.jar"
with zipfile.ZipFile(jar, 'r') as z:
    for name in z.namelist():
        if "inventory" in name.lower() and "event" in name.lower():
            print(name)
