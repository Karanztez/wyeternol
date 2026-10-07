import subprocess

jar = "build/wyeternol-server.jar"
res = subprocess.run(["javap", "-cp", jar, "net.minestom.server.inventory.Inventory"], capture_output=True, text=True)
for line in res.stdout.splitlines():
    if "item" in line.lower() or "cond" in line.lower() or "click" in line.lower():
        print(line)
