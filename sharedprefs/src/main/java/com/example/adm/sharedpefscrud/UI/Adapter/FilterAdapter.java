package com.example.adm.sharedpefscrud.UI.Adapter;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.adm.sharedpefscrud.Database.CRUDSharedPreferences;
import com.example.adm.sharedpefscrud.Model.Person;
import com.example.adm.sharedpefscrud.R;
import com.example.adm.sharedpefscrud.UI.Activities.DetailPersonActivity;
import com.example.adm.sharedpefscrud.UI.Activities.MainActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by adm on 20.01.2017.
 */

public class FilterAdapter extends ArrayAdapter<Person> implements Filterable {

    public FilterAdapter(Context context, ArrayList<Person> personList) {
        super(context, R.layout.item_person, personList);
        this.personList = personList;
        this.context = context;
        this.originPersonList = personList;
    }

    private List<Person> personList;
    private Context context;
    private Filter personFilter;
    private List<Person> originPersonList;


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;

        PersonHolder personHolder = new PersonHolder();

        if (convertView == null){

            LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.item_person, parent, false);

            TextView tvNamePerson = (TextView) view.findViewById(R.id.text_view_item_name);
            TextView tvNumberPerson = (TextView) view.findViewById(R.id.text_view_item_number);
            ImageButton imageButton = (ImageButton) view.findViewById(R.id.image_button_item_delete);

            personHolder.tvNamePerson = tvNamePerson;
            personHolder.tvPhoneNumberPerson = tvNumberPerson;
            personHolder.ibEditPerson = imageButton;

            view.setTag(personHolder);
        }
        else{
            personHolder = (PersonHolder)view.getTag();
            final Person person = personList.get(position);

            personHolder.tvNamePerson.setText(person.getName());
            personHolder.tvNamePerson.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Person.selectedPerson = person;
                    Intent detailsIntent = new Intent(context, DetailPersonActivity.class);
                    context.startActivity(detailsIntent);
                }
            });
            personHolder.tvPhoneNumberPerson.setText(person.getNumber());
            personHolder.tvPhoneNumberPerson.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel: " + person.getNumber()));
                    v.getContext().startActivity(callIntent);
                }
            });
            personHolder.ibEditPerson.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openEditDialog(person);
                }
            });
        }

        return view;
    }


    private void openDeleteDialog(final Person person){
        final AlertDialog.Builder aDialogBuilder = new AlertDialog.Builder(context);
        aDialogBuilder.setMessage("Delete Contact");
        aDialogBuilder.setMessage("Are you sure you want to delete "
                + person.getName() + " "
                + person.getSurname() + " from contact list?"
        );

        aDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                CRUDSharedPreferences crudSharedPreferences = new CRUDSharedPreferences();
                crudSharedPreferences.removePerson(context, person);
                updateListPersons();
                sendNotification(person);
            }
        });
        aDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        aDialogBuilder.setCancelable(false);
        aDialogBuilder.create();
        aDialogBuilder.show();
    }

    private void openEditDialog(final Person person) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View root = inflater.inflate(R.layout.dialog_item, null);

        final EditText id = (EditText) root.findViewById(R.id.et_dialog_id);
        final EditText name = (EditText) root.findViewById(R.id.et_dialog_nName);
        final EditText surname = (EditText) root.findViewById(R.id.et_dialog_sName);
        final EditText telephone = (EditText) root.findViewById(R.id.et_dialog_Telephone);
        final EditText mail = (EditText) root.findViewById(R.id.et_dialog_Mail);
        final EditText skype = (EditText) root.findViewById(R.id.et_dialog_Skype);

        id.setText(String.valueOf(person.getId()));
        name.setText(person.getName());
        surname.setText(person.getSurname());
        telephone.setText(person.getNumber());
        mail.setText(person.getMail());
        skype.setText(person.getSkype());

        final AlertDialog.Builder aDialogBuilder = new AlertDialog.Builder(context);
        aDialogBuilder.setView(root);
        aDialogBuilder.setMessage("Edit Contact");

        aDialogBuilder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                CRUDSharedPreferences crudSharedPreferences = new CRUDSharedPreferences();
                person.setId(Integer.parseInt(id.getText().toString()));
                person.setName(name.getText().toString());
                person.setSurname(surname.getText().toString());
                person.setNumber(telephone.getText().toString());
                person.setMail(mail.getText().toString());
                person.setSkype(skype.getText().toString());
                crudSharedPreferences.updatePerson(context, person);
                notifyDataSetChanged();
            }
        });
        aDialogBuilder.setNeutralButton("Delete", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                openDeleteDialog(person);
            }
        });
        aDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        aDialogBuilder.show();
    }

    public void dropListPersons(){
        CRUDSharedPreferences crudSharedPreferences = new CRUDSharedPreferences();
        if (!personList.isEmpty()) {
            personList.clear();
            crudSharedPreferences.deleteAllPersons(context);
        }
        notifyDataSetChanged();
    }

    public void updateListPersons(){
        CRUDSharedPreferences crudSharedPreferences = new CRUDSharedPreferences();
        if (!personList.isEmpty()) {
            personList.clear();
            personList = crudSharedPreferences.getPersons(context);
        }
        notifyDataSetChanged();
    }

    private void sendNotification(final Person personItem){
        final int NOTIFY_ID = 101;
        Context context = this.context.getApplicationContext();
        Intent notificationIntent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,
                0, notificationIntent,
                PendingIntent.FLAG_CANCEL_CURRENT);
        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification.Builder builder = new Notification.Builder(context);
        builder.setContentIntent(pendingIntent)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setWhen(System.currentTimeMillis())
                .setContentTitle("Deleted contact: " + personItem.getSurname())
                .setContentText("Follow to main Activity")
                .setAutoCancel(false);
        Notification notification = builder.getNotification();
        notificationManager.notify(NOTIFY_ID, notification);
    }


    @Override
    public int getCount() {
        return personList.size();
    }

    @Override
    public Person getItem(int position) {
        return personList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return personList.get(position).hashCode();
    }


    private static class PersonHolder{
        public TextView tvNamePerson;
        public TextView tvPhoneNumberPerson;
        public ImageButton ibEditPerson;
    }

    public void resetData(){
        personList = originPersonList;
    }


    @Override
    public Filter getFilter() {
        if (personFilter == null) {
            personFilter = new PersonFilter();
        }
        return personFilter;
    }

    private class PersonFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if (constraint == null || constraint.length() == 0){
                results.values = originPersonList;
                results.count = originPersonList.size();
            }
            else{
                List<Person> newPersonList = new ArrayList<Person>();

                for (Person p: personList) {
                    if (p.getName().toUpperCase().startsWith(constraint.toString().toUpperCase())){
                        newPersonList.add(p);
                    }
                }

                results.values = newPersonList;
                results.count = newPersonList.size();
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (results.count == 0){
                notifyDataSetInvalidated();
            }
            else{
                personList = (List<Person>)results.values;
                notifyDataSetChanged();
            }
        }
    }
}
