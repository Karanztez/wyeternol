import subprocess

jar = "build/wyeternol-server.jar"
res = subprocess.run(["javap", "-cp", jar, "net.minestom.server.inventory.Inventory"], capture_output=True, text=True)
for line in res.stdout.splitlines():
    if "setItemStack" in line or "addInventoryCondition" in line or "Inventory(" in line or "getTitle" in line:
        print(line)

res2 = subprocess.run(["javap", "-cp", jar, "net.minestom.server.entity.Player"], capture_output=True, text=True)
for line in res2.stdout.splitlines():
    if "openInventory" in line:
        print(line)
