/*
 * Copyright 2018-2019 Crown Copyright
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.gchq.gaffer.operation.impl.join.methods;

import uk.gov.gchq.gaffer.operation.impl.join.match.Match;
import uk.gov.gchq.gaffer.operation.impl.join.match.MatchKey;

import java.util.ArrayList;
import java.util.List;

public class FullJoin implements JoinFunction {
    @Override
    public List join(final List left, final List right, final Match match, final MatchKey matchKey) {
        List resultList = new ArrayList<>();
        if (matchKey.equals(MatchKey.LEFT)) {
            resultList.addAll(new OuterJoin().join(left, right, match, MatchKey.LEFT));
            resultList.addAll(new InnerJoin().join(left, right, match, MatchKey.LEFT));
            resultList.addAll(new OuterJoin().join(left, right, match, MatchKey.RIGHT));
        } else if (matchKey.equals(MatchKey.RIGHT)) {
            resultList.addAll(new OuterJoin().join(left, right, match, MatchKey.RIGHT));
            resultList.addAll(new InnerJoin().join(left, right, match, MatchKey.RIGHT));
            resultList.addAll(new OuterJoin().join(left, right, match, MatchKey.LEFT));
        }
        return resultList;
    }
}
