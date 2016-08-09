package com.hugecheng.cellularsignal;


import android.content.Context;
import android.telephony.CellInfo;
import android.telephony.CellInfoCdma;
import android.telephony.CellInfoGsm;
import android.telephony.CellInfoLte;
import android.telephony.CellInfoWcdma;
import android.telephony.CellLocation;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.util.Log;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;

public class RadioInfo {
    private int lte_MCC = Integer.MAX_VALUE;
    private int lte_MNC = Integer.MAX_VALUE;
    private int lte_CI = Integer.MAX_VALUE; //Integer.MAX_VALUE 无效
    private int lte_PCI = Integer.MAX_VALUE; //Integer.MAX_VALUE 无效
    private int lte_TAC = Integer.MAX_VALUE; //Integer.MAX_VALUE 无效
    private int lte_RSRP = Integer.MAX_VALUE; //Integer.MAX_VALUE 无效
    private int lte_RSRQ = Integer.MAX_VALUE; //Integer.MAX_VALUE 无效
    private int lte_SINR = Integer.MAX_VALUE; //Integer.MAX_VALUE 无效

    private int cdma_SID = Integer.MAX_VALUE; //Integer.MAX_VALUE 无效
    private int cdma_NID = Integer.MAX_VALUE; //Integer.MAX_VALUE 无效
    private int cdma_BSID = Integer.MAX_VALUE; //Integer.MAX_VALUE 无效
    private int cdma_RxPwr = Integer.MAX_VALUE; //-120  无效
    private int cdma_EcIo = Integer.MAX_VALUE; //-160  无效
    private int evdo_RxPwr = Integer.MAX_VALUE;
    private int evdo_EcIo = Integer.MAX_VALUE;

    private int gsm_MCC = Integer.MAX_VALUE;
    private int gsm_MNC = Integer.MAX_VALUE;
    private int gsm_LAC = Integer.MAX_VALUE;
    private int gsm_CID = Integer.MAX_VALUE;
    private int gsm_RSSI = Integer.MAX_VALUE;

    private int wcdma_MCC = Integer.MAX_VALUE;
    private int wcdma_MNC = Integer.MAX_VALUE;
    private int wcdma_LAC = Integer.MAX_VALUE;
    private int wcdma_CID = Integer.MAX_VALUE;
    private int wcdma_PSC = Integer.MAX_VALUE;
    private int wcdma_RSSI = Integer.MAX_VALUE;

    private Context mcontext;
    private TelephonyManager mTM;
    private PhoneStateMonitor mPSM;

    private final String Tag = RadioInfo.class.getName();


    public RadioInfo(Context context) {
        mcontext = context;

        mTM = (TelephonyManager) mcontext.getSystemService(Context.TELEPHONY_SERVICE);

        mPSM = new PhoneStateMonitor();
        mTM.listen(mPSM, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS | PhoneStateListener.LISTEN_CELL_INFO | PhoneStateListener.LISTEN_CELL_LOCATION);
    }


    public void RdioInfo_Exit() {
        mTM.listen(mPSM, PhoneStateListener.LISTEN_NONE);
    }

    public int getLte_MCC() {
        return lte_MCC;
    }

    public int getLte_MNC() {
        return lte_MNC;
    }

    public int getLteCI() {
        return lte_CI;
    }

    public int getLtePCI() {
        return lte_PCI;
    }

    public int getLteTAC() {
        return lte_TAC;
    }

    public int getLteRSRP() {
        return lte_RSRP;
    }

    public int getLteRSRQ() {
        return lte_RSRQ;
    }

    public int getLteSINR() {
        return lte_SINR;
    }

    public int getcdmaNID() {
        return cdma_NID;
    }

    public int getcdmaSID() {
        return cdma_SID;
    }

    public int getcdmaBSID() {
        return cdma_BSID;
    }

    public int getcdmaRxPwr() {
        return cdma_RxPwr;
    }

    public int getcdmaEcIo() {
        return cdma_EcIo;
    }

    public int getEvdo_RxPwr() {
        return evdo_RxPwr;
    }

    public int getEvdo_EcIo() {
        return evdo_EcIo;
    }

    public int getGsm_MCC() {
        return gsm_MCC;
    }

    public int getGsm_MNC() {
        return gsm_MNC;
    }

    public int getGsm_LAC() {
        return gsm_LAC;
    }

    public int getGsm_CID() {
        return gsm_CID;
    }

    public int getGsm_RSSI() {
        return gsm_RSSI;
    }

    public int getWcdma_MCC() {
        return wcdma_MCC;
    }

    public int getWcdma_MNC() {
        return wcdma_MNC;
    }

    public int getWcdma_LAC() {
        return wcdma_LAC;
    }

    public int getWcdma_CID() {
        return wcdma_CID;
    }

    public int getWcdma_PSC() {
        return wcdma_PSC;
    }

    public int getWcdma_RSSI() {
        return wcdma_RSSI;
    }

    public boolean isvalid_cdma() {
        if (cdma_RxPwr == -120)
            return false;
        else
            return true;
    }

    public boolean isvalid_lte() {
        if (lte_RSRP == Integer.MAX_VALUE)
            return false;
        else
            return true;
    }

    private class PhoneStateMonitor extends PhoneStateListener {

        @Override
        public void onSignalStrengthsChanged(SignalStrength signalStrength) {

            super.onSignalStrengthsChanged(signalStrength);
            //Log.e(Tag, signalStrength.toString());

            get_Reflection_Method(signalStrength);

            try {
                Method getLteRsrp = signalStrength.getClass().getDeclaredMethod("getLteRsrp");
                getLteRsrp.setAccessible(true);
                lte_RSRP = (int) getLteRsrp.invoke(signalStrength);

                Method getLteRsrq = signalStrength.getClass().getDeclaredMethod("getLteRsrq");
                getLteRsrq.setAccessible(true);
                lte_RSRQ = (int) getLteRsrq.invoke(signalStrength);

                Method getLteRssnr = signalStrength.getClass().getDeclaredMethod("getLteRssnr");
                getLteRssnr.setAccessible(true);
                lte_SINR = (int) getLteRssnr.invoke(signalStrength);

                cdma_RxPwr = signalStrength.getCdmaDbm();
                cdma_EcIo = signalStrength.getCdmaEcio();

                gsm_RSSI = signalStrength.getGsmSignalStrength();

                getWcdmaSignalStrength();

                ((MainActivity)mcontext).mSectionsPagerAdapter.notifyDataSetChanged();

            } catch (Exception e) {
                e.printStackTrace();
            }

            getCellIdentity();
        }

        @Override
        public void onCellInfoChanged(List<CellInfo> cellInfoList) {
            super.onCellInfoChanged(cellInfoList);


            if (cellInfoList == null) {
                //Log.e(Tag,"onCellInfoChanged is null");
                return;
            }

            //Log.e(Tag,"onCellInfoChanged size "+cellInfoList.size());

            for (CellInfo cellInfo : cellInfoList) {

                if (!cellInfo.isRegistered())
                    continue;

                if (cellInfo instanceof CellInfoLte) {

                    CellInfoLte lteinfo = (CellInfoLte) cellInfo;

                    lte_MCC = lteinfo.getCellIdentity().getMcc();
                    lte_MNC = lteinfo.getCellIdentity().getMnc();
                    lte_CI = lteinfo.getCellIdentity().getCi();
                    lte_PCI = lteinfo.getCellIdentity().getPci();
                    lte_TAC = lteinfo.getCellIdentity().getTac();
                    //Log.e(Tag,lteinfo.toString());

                } else if (cellInfo instanceof CellInfoCdma) {

                    CellInfoCdma cdmainfo = (CellInfoCdma) cellInfo;

                    cdma_SID = cdmainfo.getCellIdentity().getSystemId();
                    cdma_NID = cdmainfo.getCellIdentity().getNetworkId();
                    cdma_BSID = cdmainfo.getCellIdentity().getBasestationId();

                    //Log.e(Tag,cdmainfo.toString());
                } else if (cellInfo instanceof CellInfoGsm) {
                    CellInfoGsm gsmInfo = (CellInfoGsm) cellInfo;

                    gsm_MCC = gsmInfo.getCellIdentity().getMcc();
                    gsm_MNC = gsmInfo.getCellIdentity().getMnc();
                    gsm_CID = gsmInfo.getCellIdentity().getCid();
                    gsm_LAC = gsmInfo.getCellIdentity().getLac();

                } else if (cellInfo instanceof CellInfoWcdma) {
                    CellInfoWcdma wcdmaInfo = (CellInfoWcdma) cellInfo;

                    wcdma_MCC = wcdmaInfo.getCellIdentity().getMcc();
                    wcdma_MNC = wcdmaInfo.getCellIdentity().getMnc();
                    wcdma_CID = wcdmaInfo.getCellIdentity().getCid();
                    wcdma_LAC = wcdmaInfo.getCellIdentity().getLac();
                    wcdma_PSC = wcdmaInfo.getCellIdentity().getPsc();
                }
            }

            ((MainActivity)mcontext).mSectionsPagerAdapter.notifyDataSetChanged();
        }

        @Override
        public void onCellLocationChanged(CellLocation location) {
            super.onCellLocationChanged(location);
            //Log.e(Tag,"onCellLocationChanged");
            if (location instanceof CdmaCellLocation) {
                cdma_SID = ((CdmaCellLocation) location).getSystemId();
                cdma_NID = ((CdmaCellLocation) location).getNetworkId();
                cdma_BSID = ((CdmaCellLocation) location).getBaseStationId();
                //Log.e(Tag,((CdmaCellLocation)location).toString());
            }
            ((MainActivity)mcontext).mSectionsPagerAdapter.notifyDataSetChanged();
        }


    }

    private void getWcdmaSignalStrength() {
        List<CellInfo> cellInfoList = mTM.getAllCellInfo();

        if (cellInfoList == null) {
            //Log.e(Tag,"getAllCellInfo is null");
            return;
        }
        //Log.e(Tag,"getAllCellInfo size "+cellInfoList.size());

        for (CellInfo cellInfo : cellInfoList) {

            if (!cellInfo.isRegistered())
                continue;

            if (cellInfo instanceof CellInfoWcdma) {
                CellInfoWcdma wcdmaInfo = (CellInfoWcdma) cellInfo;
                wcdma_RSSI = wcdmaInfo.getCellSignalStrength().getDbm();
            }
        }
    }

    private void getCellIdentity() {
        List<CellInfo> cellInfoList = mTM.getAllCellInfo();

        if (cellInfoList == null) {
            //Log.e(Tag,"getAllCellInfo is null");
            return;
        }
        //Log.e(Tag,"getAllCellInfo size "+cellInfoList.size());

        for (CellInfo cellInfo : cellInfoList) {

            if (!cellInfo.isRegistered())
                continue;

            if (cellInfo instanceof CellInfoLte) {

                CellInfoLte lteinfo = (CellInfoLte) cellInfo;

                lte_MCC = lteinfo.getCellIdentity().getMcc();
                lte_MNC = lteinfo.getCellIdentity().getMnc();
                lte_CI = lteinfo.getCellIdentity().getCi();
                lte_PCI = lteinfo.getCellIdentity().getPci();
                lte_TAC = lteinfo.getCellIdentity().getTac();
                //Log.e(Tag,lteinfo.toString());

            } else if (cellInfo instanceof CellInfoCdma) {

                CellInfoCdma cdmainfo = (CellInfoCdma) cellInfo;

                cdma_SID = cdmainfo.getCellIdentity().getSystemId();
                cdma_NID = cdmainfo.getCellIdentity().getNetworkId();
                cdma_BSID = cdmainfo.getCellIdentity().getBasestationId();

                //Log.e(Tag,cdmainfo.toString());
            } else if (cellInfo instanceof CellInfoGsm) {
                CellInfoGsm gsmInfo = (CellInfoGsm) cellInfo;

                gsm_MCC = gsmInfo.getCellIdentity().getMcc();
                gsm_MNC = gsmInfo.getCellIdentity().getMnc();
                gsm_CID = gsmInfo.getCellIdentity().getCid();
                gsm_LAC = gsmInfo.getCellIdentity().getLac();

            } else if (cellInfo instanceof CellInfoWcdma) {
                CellInfoWcdma wcdmaInfo = (CellInfoWcdma) cellInfo;

                wcdma_MCC = wcdmaInfo.getCellIdentity().getMcc();
                wcdma_MNC = wcdmaInfo.getCellIdentity().getMnc();
                wcdma_CID = wcdmaInfo.getCellIdentity().getCid();
                wcdma_LAC = wcdmaInfo.getCellIdentity().getLac();
                wcdma_PSC = wcdmaInfo.getCellIdentity().getPsc();
            }
        }
    }


    public static void get_Reflection_Method(Object r) {
        String TAG = "RadioInfo ";
        Log.d(TAG, "get_Reflection_Method begin!");

        Class temp = r.getClass();
        String className = temp.getName();
        Log.d(TAG, className);
        /*
         * Note: 方法getDeclaredMethods()只能获取到由当前类定义的所有方法，不能获取从父类继承的方法
         *       方法getMethods() 不仅能获取到当前类定义的public方法，也能得到从父类继承和已经实现接口的public方法
         * 请查阅开发文档对这两个方法的详细描述。
         */
        Method[] methods = temp.getDeclaredMethods();
//        Method[] methods = temp.getMethods();

        for (int i = 0; i < methods.length; i++) {

            // 打印输出方法的修饰域
            int mod = methods[i].getModifiers();
            System.out.print(Modifier.toString(mod) + " ");

            // 输出方法的返回类型
            System.out.print(methods[i].getReturnType().getName());

            // 获取输出的方法名
            System.out.print(" " + methods[i].getName() + "(");

            // 打印输出方法的参数列表
            Class[] parameterTypes = methods[i].getParameterTypes();
            for (int j = 0; j < parameterTypes.length; j++) {
                System.out.print(parameterTypes[j].getName());
                if (parameterTypes.length > j + 1) {
                    System.out.print(", ");
                }
            }
            System.out.println(")");
        }


        Log.d(TAG, "get_Reflection_Method end!");
    }
}
