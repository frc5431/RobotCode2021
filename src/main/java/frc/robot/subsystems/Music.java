// package frc.robot.subsystems;

// import java.util.ArrayList;
// import java.util.List;

// import com.ctre.phoenix.motorcontrol.can.TalonFX;
// import com.ctre.phoenix.music.Orchestra;

// import edu.wpi.first.wpilibj2.command.SubsystemBase;

// public class Music extends SubsystemBase {
// 	TalonFX[] talons;
// 	Orchestra _orchestra;

// 	String[] _songs = new String[] {
// 		"song1.chrp",
// 		"song2.chrp",
// 		"song3.chrp",
// 		"song4.chrp",
// 		"song5.chrp",
// 		"song6.chrp",
// 		"song7.chrp",
// 		"song8.chrp",
// 		"song9.chrp", /* the remaining songs play better with three or more FXs */
// 		"song10.chrp",
// 		"song11.chrp",
// 	};

// 	/* track which song is selected for play */
//     int _songSelection = 0;

//     /* overlapped actions */
//     int _timeToPlayLoops = 0;

// 	public Music(TalonFX... talons) {
// 		this.talons = talons;

// 		/* A list of TalonFX's that are to be used as instruments */
//         ArrayList<TalonFX> _instruments = new ArrayList<TalonFX>();
      
//         /* Initialize the TalonFX's to be used */
//         for (int i = 0; i < this.talons.length; ++i) {
//             _instruments.add(this.talons[i]);
//         }
//         /* Create the orchestra with the TalonFX instruments */
// 		_orchestra = new Orchestra(_instruments);
		
// 		LoadMusicSelection(0);
// 	}

// 	void LoadMusicSelection(int offset)
//     {
//         /* increment song selection */
//         _songSelection += offset;
//         /* wrap song index in case it exceeds boundary */
//         if (_songSelection >= _songs.length) {
//             _songSelection = 0;
//         }
//         if (_songSelection < 0) {
//             _songSelection = _songs.length - 1;
//         }
//         /* load the chirp file */
//         _orchestra.loadMusic(_songs[_songSelection]); 

//         /* print to console */
//         System.out.println("Song selected is: " + _songs[_songSelection] + ".  Press 7/8 on operator to change.");
        
//         /* schedule a play request, after a delay.  
//             This gives the Orchestra service time to parse chirp file.
//             If play() is called immedietely after, you may get an invalid action error code. */
//         _timeToPlayLoops = 10;
// 	}
	
// 	@Override
// 	public void periodic() {
// 		/* if song selection changed, auto-play it */
//         if (_timeToPlayLoops > 0) {
//             --_timeToPlayLoops;
//             if (_timeToPlayLoops == 0) {
//                 /* scheduled play request */
//                 System.out.println("Auto-playing song.");
//                 _orchestra.play();
//             }
//         }

		
//         // /* has a button been pressed? */
//         // if (_lastButton != btn) {
//         //     _lastButton = btn;

//         //     switch (btn) {
//         //         case 1: /* toggle play and paused */
//         //             if (_orchestra.isPlaying()) {
//         //                 _orchestra.pause();
//         //                 System.out.println("Song paused");
//         //             }  else {
//         //                 _orchestra.play();
//         //                 System.out.println("Playing song...");
//         //             }
//         //             break;
                    
//         //         case 2: /* toggle play and stop */
//         //             if (_orchestra.isPlaying()) {
//         //                 _orchestra.stop();
//         //                 System.out.println("Song stopped.");
//         //             }  else {
//         //                 _orchestra.play();
//         //                 System.out.println("Playing song...");
//         //             }
//         //             break;
//         //     }
//         // }

//         // /* has POV/D-pad changed? */
//         // if (_lastPOV != currentPOV) {
//         //     _lastPOV = currentPOV;

//         //     switch (currentPOV) {
//         //         case 90:
//         //             /* increment song selection */
//         //             LoadMusicSelection(+1);
//         //             break;
//         //         case 270:
//         //             /* decrement song selection */
//         //             LoadMusicSelection(-1);
//         //             break;
//         //     }
// 		// }
		
// 	}
// }