echo "> STEP1# STOP"
python stop.py
echo "> STEP2# START"
./start.sh
echo "> STEP3# HEALTH"
sudo python health.py
