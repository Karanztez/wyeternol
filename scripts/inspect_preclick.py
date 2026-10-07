import subprocess

jar = "build/wyeternol-server.jar"
res = subprocess.run(["javap", "-cp", jar, "net.minestom.server.event.inventory.InventoryPreClickEvent"], capture_output=True, text=True)
print(res.stdout)
