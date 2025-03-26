package elink.suezShimla.water.crm;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import androidx.appcompat.app.AlertDialog;
import androidx.core.text.HtmlCompat;

import elink.suezShimla.water.crm.Base.App;
import elink.suezShimla.water.crm.MeterManagementSystem.MMGEntryDetails.Activity.MMGScreenActivity;

public class MessageWindow {
    public static boolean msgWindow(Context context, String msg) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(context.getString(R.string.utility_name))
                    .setCancelable(false)
                    .setMessage(msg);

            builder.setNegativeButton(context.getString(R.string.ok),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    public static boolean errorWindow(Context context, String msg) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(context.getString(R.string.alert))
                    .setCancelable(false)
                    .setMessage(msg);

            builder.setNegativeButton(context.getString(R.string.ok),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();

                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();

        } catch (Exception e) {

        }
        return false;
    }

    public static boolean msgComplaintWindow(Context context, String msg, String title, Class classname) {
        try {
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
            alertBuilder.setCancelable(false);
            alertBuilder.setTitle(title);
            alertBuilder.setMessage(msg);
            alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
//                            MainActivity m = new MainActivity();
//                            m.setBackFrag();


                    Intent i = new Intent(context, MainActivity.class);
                    i.putExtra("Tag", "0");
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//pinky added on 17/03/2022 because of backpress issue on mmg
                    context.startActivity(i);

                }
            });
            AlertDialog alert = alertBuilder.create();
            alert.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean msgComplaintComplaintWindow(Context context, String msg, String title, Class classname) {
        try {
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
            alertBuilder.setCancelable(false);
            alertBuilder.setTitle(title);
            alertBuilder.setMessage(msg);
            alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
//                            MainActivity m = new MainActivity();
//                            m.setBackFrag();


                    Intent i = new Intent(context, MainActivity.class);
                    i.putExtra("Tag", "4");
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//pinky added on 17/03/2022 because of backpress issue on mmg
                    context.startActivity(i);

                }
            });
            AlertDialog alert = alertBuilder.create();
            alert.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean msgWindowReader(Context context, String msg, String title, Class classname) {
        try {
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
            alertBuilder.setCancelable(false);
            alertBuilder.setTitle(title);
            alertBuilder.setMessage(msg);
            alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
//                            MainActivity m = new MainActivity();
//                            m.setBackFrag();


                    Intent i = new Intent(context, MainActivity.class);
                    i.putExtra("Tag", "0");
                    context.startActivity(i);

                }
            });
            AlertDialog alert = alertBuilder.create();
            alert.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean throwOutMMMFragment(Context context, String msg, String title, Class classname) {
        try {
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
            alertBuilder.setCancelable(false);
            alertBuilder.setTitle(title);
            alertBuilder.setMessage(msg);
            alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
//                            MainActivity m = new MainActivity();
//                            m.setBackFrag();

                    App.backPressMMGFragment = "Y";

                    Intent i = new Intent(context, MainActivity.class);
                    i.putExtra("Tag", "2");
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//pinky added on 03/03/2022 because of backpress issue on mmg 
                    context.startActivity(i);
                }
            });
            AlertDialog alert = alertBuilder.create();
            alert.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean successNSCFragment(Context context, String msg, String title, Class classname) {
        try {
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
            alertBuilder.setCancelable(false);
            alertBuilder.setTitle(title);
            alertBuilder.setMessage(msg);
            alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    App.backPressNSCFragment = "Y";

                    Intent i = new Intent(context, MainActivity.class);
                    i.putExtra("Tag", "3");
                    context.startActivity(i);

                }
            });
            AlertDialog alert = alertBuilder.create();
            alert.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean connectionFragment(Context context, String msg, String title, Class classname) {
        try {
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
            alertBuilder.setCancelable(false);
            alertBuilder.setTitle(title);
            alertBuilder.setMessage(msg);
            alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
//                            MainActivity m = new MainActivity();
//                            m.setBackFrag();

                    //App.backPressMMGFragment="Y";

                    //  Intent i = new Intent(context, MainActivity.class);
                    // i.putExtra("Tag", "2");
                    // context.startActivity(i);
                }
            });
            AlertDialog alert = alertBuilder.create();
            alert.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean throwOutFromWindow(Context context, String msg, String title, Class classname) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(title)
                    .setCancelable(false)
                    .setMessage(msg);

            builder.setNegativeButton(context.getString(R.string.cancel),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();

                        }
                    });
            builder.setPositiveButton(context.getString(R.string.ok),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = null;
                            intent = new Intent(context, classname);
                            context.startActivity(intent);
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void messageWindow(Context context, String msg, String title) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(title)
                    .setCancelable(false)
                    .setMessage(msg);

//            builder.setNegativeButton(context.getString(R.string.cancel),
//                    new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//
//                        }
//                    });
            builder.setPositiveButton(context.getString(R.string.ok),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

            AlertDialog alert = builder.create();
            alert.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void messageAuthenticationWindow(Context context, String msg, String title) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(title)
                    .setCancelable(false)
                    .setMessage(msg);

            builder.setNegativeButton(context.getString(R.string.cancel),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            ((MMGScreenActivity) context).onClickPrev();

                        }
                    });
            builder.setPositiveButton(context.getString(R.string.ok),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ((MMGScreenActivity) context).onClickAuthenticate();

                            dialog.dismiss();
                        }
                    });

            AlertDialog alert = builder.create();
            alert.show();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static boolean userAccess(Context context, String rights, String systemAdmin, String sytemSubAdmin) {

        boolean tmp = false;
        String[] rid = rights.split(String.valueOf(','));
        for (int i = 0; i < rid.length; i++) {
            if (rights.equals(rid[i]) || systemAdmin.equals(rid[i]) || sytemSubAdmin.equals(rid[i])) {
                tmp = true;
                break;
            }
        }
        return tmp;

    }

    public static boolean userAccess(Context context, String rights, String systemAdmin) {

        boolean tmp = false;
        String[] rid = rights.split(String.valueOf(','));
        for (int i = 0; i < rid.length; i++) {
            if (rights.equals(rid[i]) || systemAdmin.equals(rid[i])) {
                tmp = true;
                break;
            }
        }
        return tmp;

    }

// shantanu's code
    public static boolean successWindow(Context context, String msg) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(HtmlCompat.fromHtml("<font color='black'>Success</font>",HtmlCompat.FROM_HTML_MODE_LEGACY))
                    .setCancelable(false)
                    .setMessage(HtmlCompat.fromHtml("<font color='black'>"+msg+"</font>",HtmlCompat.FROM_HTML_MODE_LEGACY));
            builder.setIcon(R.drawable.success);
            builder.setNegativeButton(context.getString(R.string.ok),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        } catch (Exception e) {

        }
        return false;
    }
}
