/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RtoAppTestModule } from '../../../test.module';
import { BaseUnitDetailComponent } from 'app/entities/base-unit/base-unit-detail.component';
import { BaseUnit } from 'app/shared/model/base-unit.model';

describe('Component Tests', () => {
    describe('BaseUnit Management Detail Component', () => {
        let comp: BaseUnitDetailComponent;
        let fixture: ComponentFixture<BaseUnitDetailComponent>;
        const route = ({ data: of({ baseUnit: new BaseUnit(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RtoAppTestModule],
                declarations: [BaseUnitDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(BaseUnitDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(BaseUnitDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.baseUnit).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
