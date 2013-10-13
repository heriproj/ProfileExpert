package edu.tongji.sse.profileexpert.call;

import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import edu.tongji.sse.profileexpert.main.SendMessageConfirmDialog;


public class MyCallListener extends PhoneStateListener {
	private static int lastetState = TelephonyManager.CALL_STATE_IDLE; // ����״̬
	private Context context;

	public MyCallListener(Context context) {
		super();
		this.context = context;
	}

	public void onCallStateChanged(int state, String incomingNumber)
	{
		// �����ǰ״̬Ϊ����,�ϴ�״̬Ϊ�����еĻ�,����Ϊ��δ������
		if(lastetState ==  TelephonyManager.CALL_STATE_RINGING 
				&& state == TelephonyManager.CALL_STATE_IDLE){
			sendSmgWhenMissedCall(incomingNumber);
		}

		// ����ʱ��ı䵱ǰֵ
		lastetState = state;
	}

	private void sendSmgWhenMissedCall(final String incomingNumber)
	{
		// ... ����δ�����紦��
		Intent intent = new Intent(context, SendMessageConfirmDialog.class);
		intent.putExtra("incomingNumber", incomingNumber);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
				| Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED
				| Intent.FLAG_ACTIVITY_CLEAR_TOP
				| Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);//�ؼ���һ������������ģʽ
		context.startActivity(intent);
	}
}

