import os
import shutil
import zipfile

src_img = r"C:\Users\ACER\.gemini\antigravity-ide\brain\7380b4ef-216b-40ab-b340-fb22c0127460\wyeternol_grimoire_gui_1791385164199.jpg"

pack_dir = "resourcepack/WyEternol-Grimoire-Pack"
gui_dir = os.path.join(pack_dir, "assets/minecraft/textures/gui/container")
os.makedirs(gui_dir, exist_ok=True)

# 1. pack.mcmeta
mcmeta_content = """{
  "pack": {
    "pack_format": 46,
    "description": "WyEternol Celestial Grimoire GUI Pack"
  }
}"""
with open(os.path.join(pack_dir, "pack.mcmeta"), "w", encoding="utf-8") as f:
    f.write(mcmeta_content)

# 2. Copy and convert image to PNG generic_54.png
try:
    from PIL import Image
    with Image.open(src_img) as im:
        im.save(os.path.join(gui_dir, "generic_54.png"), "PNG")
        print("Converted to generic_54.png successfully")
except Exception as e:
    print("PIL fallback:", e)
    # If PIL not available, copy directly
    shutil.copy(src_img, os.path.join(gui_dir, "generic_54.png"))

# 3. Create ZIP pack
zip_path = "resourcepack/WyEternol-Grimoire-Pack.zip"
with zipfile.ZipFile(zip_path, "w", zipfile.ZIP_DEFLATED) as z:
    for root, dirs, files in os.walk(pack_dir):
        for file in files:
            full_path = os.path.join(root, file)
            arc_name = os.path.relpath(full_path, pack_dir)
            z.write(full_path, arc_name)

print("Created resource pack zip at:", zip_path)
