package drawingapp.states;

import drawingapp.ResizeHandle;
import drawingapp.shapes.DrawableShape;
import drawingapp.shapes.ShapeManager;

import java.util.List;

import static drawingapp.ResizeHandle.NONE;

public class DefaultState implements State{
    ShapeManager model;
    List<DrawableShape> shapes;
    List<DrawableShape> selectedShapes;
    boolean clickedAny = false;
    ResizeHandle selectedResizeHandler = NONE;

    public DefaultState(ShapeManager model) {
        this.model = model;
        shapes = model.getShapes();
        selectedShapes = model.getSelectedShapes();
    }

    @Override
    public void downClick(int x, int y, boolean shiftPressed) {
        model.setStartX(x);
        model.setStartY(y);
        if (clickedAny && !selectedShapes.isEmpty()) {      // resize handler 눌렀는지 확인
            for (DrawableShape shape : selectedShapes) {
                selectedResizeHandler = shape.resizeContains(x, y);
                model.setSelectedResizeHandler(selectedResizeHandler);
                if (selectedResizeHandler != NONE) {
                    model.setState(model.getResizeState()); // ResizeState로 상태 변경
                    break;
                }
            }
        }

        if (selectedResizeHandler == NONE) {                // resize handler 안 눌렀을 때
            for (int i = shapes.size() - 1; i >= 0; i--) {
                DrawableShape shape = shapes.get(i);
                clickedAny = false;
                if (shape.contains(x, y)) { // 도형 클릭
                    clickedAny = true;
                    if (shiftPressed) {
                        if (selectedShapes.contains(shape)) {
                            selectedShapes.remove(shape);
                            shape.setSelected(false);
                        } else {
                            selectedShapes.add(shape);
                            shape.setSelected(true);
                        }
                    } else {
                        for (DrawableShape s : selectedShapes)
                            s.setSelected(false); // 기존 선택된 개체들 선택 여부 false로 바꾸기
                        selectedShapes.clear();  // shift 없이 다른 객체 클릭 시 selectedShapes 비우기
                        selectedShapes.add(shape);
                        shape.setSelected(true);
                    }
                    model.notifyPropertyObservers();
                    model.setState(model.getMoveState());   // MoveState로 상태 변경
                    break;
                }
            }
        }

        if (!clickedAny && !shiftPressed) {     //마우스 눌렀는데 도형 선택도 안하고 shift도 안 누르면
            for (DrawableShape s : selectedShapes) s.setSelected(false);
            selectedShapes.clear();
            selectedResizeHandler = NONE;
            model.notifyPropertyObservers();
            model.setState(model.getCreateState());     // CreateState로 상태 변경
        }

        model.setPrevMouseX(x);
        model.setPrevMouseY(y);

        model.notifyDrawObservers();
    }

    @Override
    public void drag(int x, int y) {

    }

    @Override
    public void upClick(int x, int y) {

    }
}
