package kr.syeyoung.dungeonsguide.roomedit.panes;

import kr.syeyoung.dungeonsguide.dungeon.roomfinder.DungeonRoom;
import kr.syeyoung.dungeonsguide.dungeon.roomfinder.DungeonRoomInfoRegistry;
import kr.syeyoung.dungeonsguide.roomedit.MPanel;
import kr.syeyoung.dungeonsguide.roomedit.elements.*;
import kr.syeyoung.dungeonsguide.roomprocessor.ProcessorFactory;

import java.awt.*;
import java.util.ArrayList;

public class GeneralEditPane extends MPanel {
    private DungeonRoom dungeonRoom;

    private MLabelAndElement uuid;
    private MLabelAndElement name;

    private MLabelAndElement shape;
    private MLabelAndElement rotation;
    private MLabelAndElement shape2;

    private MButton save;

    private MLabelAndElement roomProcessor;

    public GeneralEditPane(final DungeonRoom dungeonRoom) {
        this.dungeonRoom = dungeonRoom;
System.out.println("building");
        {
            MLabel la;
            uuid = new MLabelAndElement("Room UUID: ", la = new MLabel());
            la.setText(dungeonRoom.getDungeonRoomInfo().getUuid().toString());
            uuid.setBounds(new Rectangle(0,0,bounds.width, 20));
            add(uuid);
        }
        {
            MTextField la = new MTextField() {
                @Override
                public void edit(String str) {
                    System.out.println(str);
                    dungeonRoom.getDungeonRoomInfo().setName(str);
                }
            };
            name = new MLabelAndElement("Room Name: ", la);
            la.setText(dungeonRoom.getDungeonRoomInfo().getName());
            name.setBounds(new Rectangle(0,20,bounds.width, 20));
            add(name);
        }

        {
            MLabel la;
            shape = new MLabelAndElement("Room Shape: ", la = new MLabel());
            la.setText(dungeonRoom.getDungeonRoomInfo().getShape()+"");
            shape.setBounds(new Rectangle(0,40,bounds.width, 20));
            add(shape);
        }

        {
            MLabel la;
            rotation = new MLabelAndElement("Found Room Rotation: ", la = new MLabel());
            la.setText(dungeonRoom.getRoomMatcher().getRotation()+"");
            rotation.setBounds(new Rectangle(0,60,bounds.width, 20));
            add(rotation);
        }
        {
            MLabel la;
            shape2 = new MLabelAndElement("Found Room Shape: ", la = new MLabel());
            la.setText(dungeonRoom.getShape()+"");
            shape2.setBounds(new Rectangle(0,80,bounds.width, 20));
            add(shape2);
        }
        {
            final MStringSelectionButton mStringSelectionButton = new MStringSelectionButton(new ArrayList<String>(ProcessorFactory.getProcessors()));
            roomProcessor = new MLabelAndElement("Room Processor: ", mStringSelectionButton);
            roomProcessor.setBounds(new Rectangle(0,100,bounds.width, 20));
            add(roomProcessor);

            mStringSelectionButton.setOnUpdate(new Runnable() {
                @Override
                public void run() {
                    dungeonRoom.getDungeonRoomInfo().setProcessorId(mStringSelectionButton.getSelected());
                    dungeonRoom.updateRoomProcessor();
                }
            });
        }
        {
            if (dungeonRoom.getDungeonRoomInfo().isRegistered()) return;
            save = new MButton();
            save.setText("Save RoomData");
            save.setOnActionPerformed(new Runnable() {
                @Override
                public void run() {
                    DungeonRoomInfoRegistry.register(dungeonRoom.getDungeonRoomInfo());
                    remove(save);
                }
            });
            save.setBackgroundColor(Color.green);
            save.setBounds(new Rectangle(1,120,bounds.width-2, 20));
            add(save);
            System.out.println(save.getBounds());
        }
    }

    @Override
    public void resize(int parentWidth, int parentHeight) {
        this.setBounds(new Rectangle(5,5,parentWidth-10,parentHeight-10));
    }

    @Override
    public void onBoundsUpdate() {
        if (save != null)
            save.setBounds(new Rectangle(0,120,bounds.width, 20));
    }
}