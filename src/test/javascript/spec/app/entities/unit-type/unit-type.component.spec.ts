/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { RtoAppTestModule } from '../../../test.module';
import { UnitTypeComponent } from 'app/entities/unit-type/unit-type.component';
import { UnitTypeService } from 'app/entities/unit-type/unit-type.service';
import { UnitType } from 'app/shared/model/unit-type.model';

describe('Component Tests', () => {
    describe('UnitType Management Component', () => {
        let comp: UnitTypeComponent;
        let fixture: ComponentFixture<UnitTypeComponent>;
        let service: UnitTypeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RtoAppTestModule],
                declarations: [UnitTypeComponent],
                providers: []
            })
                .overrideTemplate(UnitTypeComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(UnitTypeComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(UnitTypeService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new UnitType(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.unitTypes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
