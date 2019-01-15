package com.mrcrayfish.modelcreator.panels;

import com.mrcrayfish.modelcreator.ModelCreator;
import com.mrcrayfish.modelcreator.StateManager;
import com.mrcrayfish.modelcreator.element.Element;
import com.mrcrayfish.modelcreator.element.ElementManager;
import com.mrcrayfish.modelcreator.util.ComponentUtil;

import javax.swing.*;
import java.awt.*;

public class FaceExtrasPanel extends JPanel implements IElementUpdater
{
    private ElementManager manager;

    private JPanel horizontalBox;
    private JRadioButton boxCullFace;
    private JRadioButton boxFill;
    private JRadioButton boxEnabled;
    private JRadioButton boxAutoUV;

    public FaceExtrasPanel(ElementManager manager)
    {
        this.manager = manager;
        this.setBackground(ModelCreator.BACKGROUND);
        this.setLayout(new BorderLayout(0, 5));
        this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(ModelCreator.BACKGROUND, 5), "<html><b>Extras</b></html>"));
        this.setMaximumSize(new Dimension(186, 100));
        this.initComponents();
        this.addComponents();
    }

    private void initComponents()
    {
        horizontalBox = new JPanel(new GridLayout(2, 2));
        boxCullFace = ComponentUtil.createRadioButton("Cullface", "<html>Should render face is another block is adjacent<br>Default: Off</html>");
        boxCullFace.setBackground(ModelCreator.BACKGROUND);
        boxCullFace.addActionListener(e ->
        {
            Element selectedElement = manager.getSelectedElement();
            if(selectedElement != null)
            {
                selectedElement.getSelectedFace().setCullface(boxCullFace.isSelected());
                StateManager.pushState(manager);
            }
        });
        boxFill = ComponentUtil.createRadioButton("Fill", "<html>Makes the texture fill the face<br>Default: Off</html>");
        boxFill.setBackground(ModelCreator.BACKGROUND);
        boxFill.addActionListener(e ->
        {
            Element selectedElement = manager.getSelectedElement();
            if(selectedElement != null)
            {
                selectedElement.getSelectedFace().fitTexture(boxFill.isSelected());
                StateManager.pushState(manager);
            }
        });
        boxEnabled = ComponentUtil.createRadioButton("Enable", "<html>Determines if face should be rendered<br>Default: On</html>");
        boxEnabled.setBackground(ModelCreator.BACKGROUND);
        boxEnabled.addActionListener(e ->
        {
            Element selectedElement = manager.getSelectedElement();
            if(selectedElement != null)
            {
                selectedElement.getSelectedFace().setEnabled(boxEnabled.isSelected());
                StateManager.pushState(manager);
            }
        });
        boxAutoUV = ComponentUtil.createRadioButton("Auto UV", "<html>Determines if UV end coordinates should be set based on element size<br>Default: On</html>");
        boxAutoUV.setBackground(ModelCreator.BACKGROUND);
        boxAutoUV.addActionListener(e ->
        {
            Element selectedElement = manager.getSelectedElement();
            if(selectedElement != null)
            {
                selectedElement.getSelectedFace().setAutoUVEnabled(boxAutoUV.isSelected());
                selectedElement.getSelectedFace().updateEndUV();
                manager.updateValues();
                StateManager.pushState(manager);
            }
        });
        horizontalBox.add(boxCullFace);
        horizontalBox.add(boxFill);
        horizontalBox.add(boxEnabled);
        horizontalBox.add(boxAutoUV);

    }

    private void addComponents()
    {
        add(horizontalBox, BorderLayout.NORTH);
    }

    @Override
    public void updateValues(Element cube)
    {
        if(cube != null)
        {
            boxCullFace.setEnabled(true);
            boxCullFace.setSelected(cube.getSelectedFace().isCullfaced());
            boxFill.setEnabled(true);
            boxFill.setSelected(cube.getSelectedFace().shouldFitTexture());
            boxEnabled.setEnabled(true);
            boxEnabled.setSelected(cube.getSelectedFace().isEnabled());
            boxAutoUV.setEnabled(true);
            boxAutoUV.setSelected(cube.getSelectedFace().isAutoUVEnabled());
        }
        else
        {
            boxCullFace.setEnabled(false);
            boxCullFace.setSelected(false);
            boxFill.setEnabled(false);
            boxFill.setSelected(false);
            boxEnabled.setEnabled(false);
            boxEnabled.setSelected(false);
            boxAutoUV.setEnabled(false);
            boxAutoUV.setSelected(false);
        }
    }
}
