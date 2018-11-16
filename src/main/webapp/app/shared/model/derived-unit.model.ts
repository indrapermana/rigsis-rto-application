import { IBaseUnit } from 'app/shared/model//base-unit.model';

export interface IDerivedUnit {
    id?: number;
    name?: string;
    symbol?: string;
    conversionType?: string;
    factor?: number;
    origin?: string;
    baseUnit?: IBaseUnit;
}

export class DerivedUnit implements IDerivedUnit {
    constructor(
        public id?: number,
        public name?: string,
        public symbol?: string,
        public conversionType?: string,
        public factor?: number,
        public origin?: string,
        public baseUnit?: IBaseUnit
    ) {}
}
