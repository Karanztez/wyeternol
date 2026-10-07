import subprocess

jar = "build/wyeternol-server.jar"
res = subprocess.run(["javap", "-cp", jar, "net.minestom.server.inventory.Inventory"], capture_output=True, text=True)
for line in res.stdout.splitlines():
    if "setitem" in line.lower() or "condition" in line.lower():
        print(line)

res2 = subprocess.run(["javap", "-cp", jar, "net.minestom.server.item.ItemStack"], capture_output=True, text=True)
for line in res2.stdout.splitlines():
    if "builder" in line.lower() or "of(" in line.lower():
        print(line)
