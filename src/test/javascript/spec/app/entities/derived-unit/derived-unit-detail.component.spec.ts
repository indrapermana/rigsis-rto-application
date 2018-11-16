/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RtoAppTestModule } from '../../../test.module';
import { DerivedUnitDetailComponent } from 'app/entities/derived-unit/derived-unit-detail.component';
import { DerivedUnit } from 'app/shared/model/derived-unit.model';

describe('Component Tests', () => {
    describe('DerivedUnit Management Detail Component', () => {
        let comp: DerivedUnitDetailComponent;
        let fixture: ComponentFixture<DerivedUnitDetailComponent>;
        const route = ({ data: of({ derivedUnit: new DerivedUnit(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RtoAppTestModule],
                declarations: [DerivedUnitDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(DerivedUnitDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(DerivedUnitDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.derivedUnit).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
