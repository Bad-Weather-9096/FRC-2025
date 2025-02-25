# Robot Operation
<img src="360_controller.png" width="660px"/>

## Drive Controller
* Left stick (field-relative)
  * up/down - advance/retreat
  * left/right - strafe
* Right stick
  * left/right - spin
* D-pad - nudge (robot-relative)

## Auxilliary Controller
* Left stick
  * up/down - raise/lower elevator
* Right stick
  * up/down - raise/lower end effector
* D-pad
  * up/down - select level (reef)
  * left/right - select position (reef and coral station)
* A-button - engage auto-pilot
* X-button - extract algae
* Left/right bumper - pick up/drop coral 
* Left/right trigger - pick up/drop algae

## Autonomous Mode
Robot will not be pre-loaded with coral. Robot will reverse approximately 7 feet and then rotate until a reef target is identified. Auto-pilot will guide the robot to the target, where it will position the end effector for algae retrieval. First action by drive team will be to pick up the algae and deliver it to the processor.

## Notes
* Auto-pilot can be engaged before a tag is identified; however, once activated, the robot will lock onto the first tag it sees. Make sure you are pointing in the right direction before you press the A button.
* Auto-pilot overrides manual drive control. Releasing the A button disengages auto-pilot and returns control to the driver.
* Position adjustment and algae extraction also override manual drive control. These actions cannot be canceled once initiated.
* Level/position selection and algae extraction require prior acquisition of a target lock via auto-pilot. If a target has not been identified, the controls that trigger these actions will do nothing.
* Level selection is cargo-dependent. If the robot is carrying coral, the up/down buttons will select the appropriate coral release level. Otherwise, they will select the appropriate algae extraction level.
* Resuming manual drive control clears any existing target lock. With the exception of nudge operations, if the robot is moved after an auto-pilot sequence has been initiated, you will need to re-target.
* Manual elevator and end effector control may disrupt automated behaviors and should be used sparingly, if at all.
