/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { RtoAppTestModule } from '../../../test.module';
import { RigComponent } from 'app/entities/rig/rig.component';
import { RigService } from 'app/entities/rig/rig.service';
import { Rig } from 'app/shared/model/rig.model';

describe('Component Tests', () => {
    describe('Rig Management Component', () => {
        let comp: RigComponent;
        let fixture: ComponentFixture<RigComponent>;
        let service: RigService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RtoAppTestModule],
                declarations: [RigComponent],
                providers: []
            })
                .overrideTemplate(RigComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(RigComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RigService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Rig(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.rigs[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
