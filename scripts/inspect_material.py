import subprocess

jar = "build/wyeternol-server.jar"
res = subprocess.run(["javap", "-cp", jar, "net.minestom.server.item.Material"], capture_output=True, text=True)
for line in res.stdout.splitlines():
    if "from" in line.lower():
        print(line)
