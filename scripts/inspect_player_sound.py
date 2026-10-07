import subprocess

jar = "build/wyeternol-server.jar"
res = subprocess.run(["javap", "-cp", jar, "net.minestom.server.entity.Player"], capture_output=True, text=True)
for line in res.stdout.splitlines():
    if "sound" in line.lower():
        print(line)
