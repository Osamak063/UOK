package com.example.osamakhalid.schoolsystem.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.osamakhalid.schoolsystem.Model.ItemClickListener;
import com.example.osamakhalid.schoolsystem.Model.MessagesFavResponse;
import com.example.osamakhalid.schoolsystem.Model.MessagesInboxResponse;
import com.example.osamakhalid.schoolsystem.Model.MessagesSentResponse;
import com.example.osamakhalid.schoolsystem.Model.MessagesTrashResponse;
import com.example.osamakhalid.schoolsystem.Model.TeacherData_Model;
import com.example.osamakhalid.schoolsystem.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Osama Khalid on 2/26/2018.
 */

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.ViewHolder> implements Filterable {
    Context context;
    List<MessagesInboxResponse> inboxList;
    List<MessagesSentResponse> sentList;
    List<MessagesFavResponse> favList;
    List<MessagesTrashResponse> trashList;
    private List<MessagesInboxResponse> backupInbox;
    private List<MessagesSentResponse> backupSent;
    private List<MessagesFavResponse> backupFav;
    private List<MessagesTrashResponse> backupTrash;
    private String type = "inbox";
    private ItemClickListener itemClickListener;

    public MessagesAdapter(List<MessagesInboxResponse> inboxList, Context context) {
        this.context = context;
        this.inboxList = inboxList;
        this.backupInbox = inboxList;
    }

    public MessagesAdapter(List<MessagesSentResponse> sentList, Context context, String type) {
        this.context = context;
        this.sentList = sentList;
        this.backupSent = sentList;
        this.type = type;
    }

    public MessagesAdapter(Context context, String type, List<MessagesFavResponse> favList) {
        this.context = context;
        this.favList = favList;
        this.backupFav = favList;
        this.type = type;
    }

    public MessagesAdapter(Context context, List<MessagesTrashResponse> trashList, String type) {
        this.context = context;
        this.trashList = trashList;
        this.backupTrash = trashList;
        this.type = type;
    }

    @Override
    public MessagesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.inbox_view_layout, parent, false);
        return new MessagesAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MessagesAdapter.ViewHolder holder, int position) {
        if (type.equals("inbox")) {
            final MessagesInboxResponse inbox = inboxList.get(position);
            holder.name.setText("Name: " + inbox.getName());
            holder.subject.setText("Subject: " + inbox.getSubject());
            holder.time.setText("Time: " + inbox.getTime());
            if (inbox.getAttach().equals("yes")) {
                holder.attachmentIcon.setVisibility(View.VISIBLE);
            }
        } else if (type.equals("sent")) {
            final MessagesSentResponse sent = sentList.get(position);
            holder.name.setText("To: " + sent.getTo());
            holder.subject.setText("Subject: " + sent.getSubject());
            holder.time.setText("Time: " + sent.getTime());
            if (sent.getAttachment().equals("yes")) {
                holder.attachmentIcon.setVisibility(View.VISIBLE);
            }
        } else if (type.equals("fav")) {
            final MessagesFavResponse fav = favList.get(position);
            holder.name.setText("Name: " + fav.getName());
            holder.subject.setText("Subject: " + fav.getSubject());
            holder.time.setText("Time: " + fav.getTime());
            if (fav.getAttachment().equals("yes")) {
                holder.attachmentIcon.setVisibility(View.VISIBLE);
            }
        } else if (type.equals("trash")) {
            final MessagesTrashResponse trash = trashList.get(position);
            holder.name.setText("Sender Name: " + trash.getSender());
            holder.subject.setText("Subject: " + trash.getSubject());
            holder.time.setText("Time: " + trash.getTime());
            if (trash.getAttachment().equals("yes")) {
                holder.attachmentIcon.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public int getItemCount() {
        if (type.equals("inbox")) {
            return inboxList.size();
        } else if (type.equals("sent")) {
            return sentList.size();
        } else if (type.equals("fav")) {
            return favList.size();
        } else if (type.equals("trash")) {
            return trashList.size();
        }

        return inboxList.size();
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    if (type.equals("inbox")) {
                        inboxList = backupInbox;
                    } else if (type.equals("sent")) {
                        sentList = backupSent;
                    } else if (type.equals("fav")) {
                        favList = backupFav;
                    } else if (type.equals("trash")) {
                        trashList = backupTrash;
                    }
                } else {
                    if (type.equals("inbox")) {
                        List<MessagesInboxResponse> filteredList = new ArrayList<>();
                        for (MessagesInboxResponse row : backupInbox) {
                            if (row.getName().toLowerCase().contains(charString.toLowerCase())) {
                                filteredList.add(row);
                            }
                        }

                        inboxList = filteredList;
                    } else if (type.equals("sent")) {
                        List<MessagesSentResponse> filteredList = new ArrayList<>();
                        for (MessagesSentResponse row : backupSent) {
                            if (row.getTo().toLowerCase().contains(charString.toLowerCase())) {
                                filteredList.add(row);
                            }
                        }

                        sentList = filteredList;
                    } else if (type.equals("fav")) {
                        List<MessagesFavResponse> filteredList = new ArrayList<>();
                        for (MessagesFavResponse row : backupFav) {
                            if (row.getName().toLowerCase().contains(charString.toLowerCase())) {
                                filteredList.add(row);
                            }
                        }

                        favList = filteredList;
                    } else if (type.equals("trash")) {
                        List<MessagesTrashResponse> filteredList = new ArrayList<>();
                        for (MessagesTrashResponse row : backupTrash) {
                            if (row.getSender().toLowerCase().contains(charString.toLowerCase())) {
                                filteredList.add(row);
                            }
                        }

                        trashList = filteredList;
                    }
                }

                FilterResults filterResults = new FilterResults();
                if (type.equals("inbox")) {
                    filterResults.values = inboxList;
                } else if (type.equals("sent")) {
                    filterResults.values = sentList;
                } else if (type.equals("fav")) {
                    filterResults.values = favList;
                } else if (type.equals("fav")) {
                    filterResults.values = trashList;
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                if (type.equals("inbox")) {
                    inboxList = (ArrayList<MessagesInboxResponse>) filterResults.values;
                    notifyDataSetChanged();
                } else if (type.equals("sent")) {
                    sentList = (ArrayList<MessagesSentResponse>) filterResults.values;
                    notifyDataSetChanged();
                } else if (type.equals("fav")) {
                    favList = (ArrayList<MessagesFavResponse>) filterResults.values;
                    notifyDataSetChanged();
                } else if (type.equals("trash")) {
                    trashList = (ArrayList<MessagesTrashResponse>) filterResults.values;
                    notifyDataSetChanged();
                }
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView name, subject, time;
        ImageView attachmentIcon;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.inbox_name);
            subject = itemView.findViewById(R.id.inbox_subject);
            time = itemView.findViewById(R.id.inbox_time);
            attachmentIcon = itemView.findViewById(R.id.inbox_attachment_icon);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (itemClickListener != null) {
                if (type.equals("inbox")) {
                    MessagesInboxResponse inbox = inboxList.get(getAdapterPosition());
                    itemClickListener.onClick(view, inbox.getMessageId());
                } else if (type.equals("sent")) {
                    MessagesSentResponse sent = sentList.get(getAdapterPosition());
                    itemClickListener.onClick(view, sent.getMessageId());
                } else if (type.equals("fav")) {
                    MessagesFavResponse fav = favList.get(getAdapterPosition());
                    itemClickListener.onClick(view, fav.getMessageId());
                } else if (type.equals("trash")) {
                    MessagesTrashResponse trash = trashList.get(getAdapterPosition());
                    itemClickListener.onClick(view, trash.getMessageId());
                }
            }
        }
    }
}


