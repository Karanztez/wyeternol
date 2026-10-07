import urllib.request
import json
import zipfile
import io

# Get job log URL
url = "https://api.github.com/repos/Karanztez/wyeternol/actions/jobs/112816078956/logs"
req = urllib.request.Request(url, headers={"User-Agent": "Mozilla/5.0"})
try:
    with urllib.request.urlopen(req) as resp:
        log_text = resp.read().decode('utf-8', errors='ignore')
        # Print lines around 'FAILED' or 'error'
        lines = log_text.splitlines()
        failed_lines = [l for l in lines if "FAILED" in l or "error" in l.lower() or "40" in l or "50" in l]
        print(f"Total log lines: {len(lines)}")
        for l in lines[-40:]:
            print(l)
except Exception as e:
    print(f"Error fetching logs: {e}")
