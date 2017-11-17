package org.rookit.mongodb.update;

import org.rookit.dm.play.PlayableSetter;

@SuppressWarnings("javadoc")
public interface PlayableUpdateQuery<Q extends PlayableUpdateQuery<Q, S>, S extends PlayableUpdateFilterQuery<S>> extends RookitUpdateQuery<S>, PlayableSetter<Q> {
	
	//

}
