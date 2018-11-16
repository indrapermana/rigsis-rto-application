import { IUnitType } from 'app/shared/model//unit-type.model';
import { IDerivedUnit } from 'app/shared/model//derived-unit.model';

export interface IBaseUnit {
    id?: number;
    name?: string;
    symbol?: string;
    description?: string;
    origin?: string;
    unitTypes?: IUnitType[];
    derivedUnits?: IDerivedUnit[];
}

export class BaseUnit implements IBaseUnit {
    constructor(
        public id?: number,
        public name?: string,
        public symbol?: string,
        public description?: string,
        public origin?: string,
        public unitTypes?: IUnitType[],
        public derivedUnits?: IDerivedUnit[]
    ) {}
}
