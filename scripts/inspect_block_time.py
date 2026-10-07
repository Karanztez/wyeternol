import subprocess

jar = "build/wyeternol-server.jar"
res = subprocess.run(["javap", "-cp", jar, "net.minestom.server.instance.block.Block"], capture_output=True, text=True)
for line in res.stdout.splitlines():
    if "from" in line.lower():
        print("Block:", line)

res2 = subprocess.run(["javap", "-cp", jar, "net.minestom.server.instance.Instance"], capture_output=True, text=True)
for line in res2.stdout.splitlines():
    if "time" in line.lower():
        print("Instance:", line)
