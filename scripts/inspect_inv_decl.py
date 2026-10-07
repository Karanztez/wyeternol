import subprocess

jar = "build/wyeternol-server.jar"
res = subprocess.run(["javap", "-cp", jar, "net.minestom.server.inventory.Inventory"], capture_output=True, text=True)
for line in res.stdout.splitlines()[:20]:
    print(line)
